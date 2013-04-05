package org.p3aches.jobs.spawner;

import org.p3aches.p3agile.P3AVars;
import org.p3aches.p3agile.States;
import org.p3aches.p3spawner.P3SVars;
import org.p3aches.p3spawner.P3Spawner;
import org.p3aches.p3spawner.StatesSpawn;
import org.p3aches.utils.P3Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;

/**
 * Created with IntelliJ IDEA.
 * User: C0r31N
 * Date: 3/11/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class WalkBack extends Node {
    static StatesSpawn THIS_STATE = StatesSpawn.WALK_BACK;
    Item summPot;
    SceneObject obliskSO;
    GroundItem[]  allFruit;

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
        if(CastScroll.castTile == null){
            CastScroll.castTile = P3Util.getRandTile(P3Spawner.spawnArea);
        }
        allFruit = GroundItems.getLoaded(PickItem.pickUpFilter);
        System.out.println("Dist to walkback: "+Players.getLocal().getLocation().getLocation().distance(CastScroll.castTile)
                +"eval: "+(Players.getLocal().getLocation().getLocation().distance(CastScroll.castTile) > 0));
        return !(((Players.getLocal().getLocation().getLocation().distance(CastScroll.castTile) > 0   && allFruit.length == 0)
                ||(Players.getLocal().getLocation().getLocation().distance(CastScroll.castTile) > 4))
        && BankItem.bank == false
        && !new Widget(11).getChild(1).visible());
    }

    @Override
    public void execute() {
        if(Players.getLocal().getLocation().getLocation().distance(CastScroll.castTile) < 14){
            if(!CastScroll.castTile.isOnScreen()){
                CastScroll.castTile.clickOnMap();
                P3Util.waitFor(2,new P3Util.Cond() {  //Wait if not idle
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 300);
                        return (-1 != Players.getLocal().getAnimation());
                    }
                });
            }else{
                CastScroll.castTile.interact("Walk here");
                System.out.println("Walk back");
                P3Util.waitFor(2,new P3Util.Cond() {  //Wait if not idle
                    @Override
                    public boolean accept() {
                        System.out.println("Sleeping while walking back");
                        Task.sleep(200, 300);
                        return (-1 != Players.getLocal().getAnimation()
                                || Players.getLocal().getLocation().distance(CastScroll.castTile) == 0);
                    }
                });
            }
        }
        else if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.OBELISK){
            P3Util.walkPath(P3SVars.toObelisk,false,25);
        }
    }
}
