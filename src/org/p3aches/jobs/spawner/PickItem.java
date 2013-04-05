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
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.util.Filter;


public class PickItem extends Node implements MessageListener {

    Camera camera;
    Widget closeClan;
    GroundItem[]  allFruit;
    Item summPot;

    public static int papCnt = 0,orangCnt = 0,banCnt = 0, totalInvCnt = 0,totalPapCnt = 0,totalOrangCnt = 0,totalBanCnt =0
            ,banPrice =0,papPrice =0,orangePrice=0, eggPrice = 0, eggCnt = 0, totalEggCnt =0;
    public static double totalPrice = 0;

    final public static Filter<GroundItem> pickUpFilter = new Filter<GroundItem>() {
        @Override
        public boolean accept(GroundItem fruit) {
            if(CastScroll.castTile == null){
                CastScroll.castTile = P3Util.getRandTile(P3Spawner.spawnArea);
            }
            return ((fruit.getLocation().distance(CastScroll.castTile)<5)
                    && ((fruit.getId() == P3SVars.PAPAYA)
                    ||(fruit.getId() == P3SVars.ORANGE)
                    ||(fruit.getId() == P3SVars.BANNANA))
                    ||(fruit.getId() == P3SVars.SPIDER_EGG));
        }
    };
    final public static Filter<Item> sumFilter = new Filter<Item>() {
        @Override
        public boolean accept(Item item) {

            return (item.getId() == P3SVars.SUM_POT1
                    || item.getId() == P3SVars.SUM_POT2
                    || item.getId() == P3SVars.SUM_POT3
                    || item.getId() == P3SVars.SUM_POT4);
        }
    };
    public static boolean pickingUp = false;

    static StatesSpawn THIS_STATE = StatesSpawn.PICK_UP_ITEM;

    @Override
    public boolean activate() {
        final boolean valid = !isDone();

//        System.out.println("Pick up Item valid:"+valid);
        if ((valid && (P3Spawner.curState == StatesSpawn.NONE))) {
            P3Spawner.curState = THIS_STATE;
        } else if (!valid && (P3Spawner.curState == THIS_STATE)) {
            P3Spawner.curState = StatesSpawn.NONE;
        }
        return ((P3Spawner.curState == THIS_STATE) && valid);
    }

    private boolean isDone() {

        allFruit = GroundItems.getLoaded(pickUpFilter);
        if(allFruit.length == 0 && !BankItem.bank){//Go back to tile
            pickingUp = false;
        }
//        System.out.println("Min: "+Math.min(5,28 - Inventory.getCount()));
        if(allFruit.length >= Math.min(5,28 - Inventory.getCount())){
            pickingUp = true;
        }
        if(((Summoning.getSpecialPoints() < 6) && (allFruit.length > 0))){
            pickingUp = true;
        }
        if(P3Spawner.curState == StatesSpawn.NONE && allFruit.length>0){
            pickingUp = true;
        }
        System.out.println("Enter Pick Payaya: "+(pickingUp == true && !Inventory.isFull()));
        return !(pickingUp == true
                && !Inventory.isFull()
                && Summoning.getFamiliar() != null);
    }

    @Override
    public void execute() {
        closeClan = new Widget(205);
        if( closeClan.getChild(0).visible()){
            closeClan.getChild(62).click(true);
        }
        System.out.println("fruit:" + allFruit.length);
        if(allFruit.length>0){
            if(!allFruit[0].isOnScreen()){
                allFruit[0].getLocation().clickOnMap();
                Task.sleep(50,70);
                P3Util.turnTo(allFruit[0].getLocation(),camera);
            }
            else{
                allFruit[0].interact("Take");
                P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 300);
                        return (-1 == Players.getLocal().getAnimation());
                    }
                });
            }
        }
        if(Inventory.getCount() != totalInvCnt){
            totalInvCnt =  Inventory.getCount();
            if(Inventory.getCount(P3SVars.PAPAYA) != papCnt){
                totalPapCnt += 1;
                papCnt = Inventory.getCount(P3SVars.PAPAYA);
                totalPrice+= papPrice;
            }
            if(Inventory.getCount(P3SVars.ORANGE) != orangCnt){
                totalOrangCnt += 1;
                orangCnt = Inventory.getCount(P3SVars.ORANGE);
                totalPrice+= orangePrice;
            }
            if(Inventory.getCount(P3SVars.BANNANA) != banCnt){
                totalBanCnt += 1;
                banCnt = Inventory.getCount(P3SVars.BANNANA);
                totalPrice+= banPrice;
            }
            if(Inventory.getCount(P3SVars.SPIDER_EGG) != eggCnt){
                totalEggCnt += 1;
                eggCnt = Inventory.getCount(P3SVars.SPIDER_EGG);
                totalPrice+= eggPrice;
            }

        }
    }

    public static void clearCnters(){
        papCnt = 0;
        orangCnt = 0;
        banCnt = 0;
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        if(messageEvent.getMessage().toLowerCase().contains("your familiar has produced an item.")){
            Summoning.takeBoB();
        }
        System.out.println("Message: "+ messageEvent.getMessage().toLowerCase().toString());
    }
}
