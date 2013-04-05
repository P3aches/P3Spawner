package org.p3aches.jobs.spawner;


import org.p3aches.p3agile.P3AVars;
import org.p3aches.p3agile.States;
import org.p3aches.p3spawner.P3SVars;
import org.p3aches.p3spawner.P3Spawner;
import org.p3aches.p3spawner.StatesSpawn;
import org.p3aches.utils.P3Util;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.widget.Widget;


public class CastScroll extends Node implements MessageListener{

	Camera camera;
	Widget castButton;
    GroundItem[] allFruit;
	static StatesSpawn THIS_STATE = StatesSpawn.CAST_FRUIT_FALL;
    public static Tile castTile;
    boolean callFam = false, takeBOB = false;
    public static int scrollCnt = -1, scrollPrice = 0;

	@Override
	public boolean activate() {
		final boolean valid = !isDone();
		
		if ((valid && (P3Spawner.curState == StatesSpawn.NONE))) {
			P3Spawner.curState = THIS_STATE;
		} else if (!valid && (P3Spawner.curState == THIS_STATE)) {
            P3Spawner.curState = StatesSpawn.NONE;
		}
		return ((P3Spawner.curState == THIS_STATE) && valid);
	}

	private boolean isDone() {
        allFruit = GroundItems.getLoaded(PickItem.pickUpFilter);
        if(allFruit.length>=Math.min(5,28 - Inventory.getCount()))
            PickItem.pickingUp = true;
       // System.out.println("PickItem.pickingUp: " + PickItem.pickingUp);
        NPC fruitBat = Summoning.getFamiliar();
		return !( (Summoning.getSpecialPoints()>=6)
        && (Inventory.getCount(true, P3SVars.scroll)>0)
        && (!PickItem.pickingUp)
        && !Inventory.isFull()
        && fruitBat != null
        && Players.getLocal().getLocation().distance(castTile)==0);
	}

	@Override
	public void execute() {
        if(Inventory.getCount(true,P3SVars.scroll) != scrollCnt){
            P3SVars.scrollsUsed += 1;
            scrollCnt = Inventory.getCount(true,P3SVars.scroll);
            PickItem.totalPrice -= CastScroll.scrollPrice;
        }
        if(castTile == null){//Set the null tile
            castTile = P3Util.getRandTile(P3Spawner.spawnArea);
        }

        if(callFam){// Bring back follower
            Summoning.select(Summoning.Option.CALL_FOLLOWER);
            Mouse.click(true);
            callFam = false;
        }
        else if(takeBOB){ //Get items from fam
            Summoning.select(Summoning.Option.TAKE_BOB);
            Mouse.click(true);
            takeBOB = false;
        }
        else if(Settings.get(1790) == 0x01 || Summoning.getLeftClickOption().equals(Summoning.Option.CAST)
                && Players.getLocal().getLocation().distance(castTile)==0
                && !callFam){//Cast Scroll
            castButton = new Widget(747);
            castButton.getChild(18).click(true);
            P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
                @Override
                public boolean accept() {
                    Task.sleep(500, 700);
                    return (-1 == Players.getLocal().getAnimation());
                }
            });
        }
        else if(!Summoning.getLeftClickOption().equals(Summoning.Option.CAST)){
            Summoning.setLeftClickOption(Summoning.Option.CAST);
            P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
                @Override
                public boolean accept() {
                    Task.sleep(500, 700);
                    return ( Summoning.getLeftClickOption().equals(Summoning.Option.CAST));
                }
            });

        }
//        else if(Players.getLocal().getLocation().distance(castTile)>0){//Go back to tile
//            if(!castTile.isOnScreen()){ //If tile not on screen click map
//                castTile.clickOnMap();
//                P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
//                    @Override
//                    public boolean accept() {
//                        Task.sleep(200, 300);
//                        return (-1 == Players.getLocal().getAnimation());
//                    }
//                });
//            }
//            else{ //if tile is on screen click tile
//                castTile.click(true);
//                P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
//                    @Override
//                    public boolean accept() {
//                        Task.sleep(200, 300);
//                        return (-1 == Players.getLocal().getAnimation());
//                    }
//                });
//            }
//        }
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        if(messageEvent.getMessage().toLowerCase().contains("your familiar is too far away to use that scroll, or it cannot see you.")){
            callFam = true;
        }
        if(messageEvent.getMessage().toLowerCase().contains("your familiar has produced an item.")){
            takeBOB = true;
            System.out.println("Take BOB");
        }

        System.out.println("Message: "+ messageEvent.getMessage().toLowerCase().toString());
    }
}
