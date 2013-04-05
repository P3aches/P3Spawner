package org.p3aches.jobs.spawner;


import org.p3aches.p3agile.P3AVars;
import org.p3aches.p3agile.States;
import org.p3aches.p3spawner.P3SVars;
import org.p3aches.p3spawner.P3Spawner;
import org.p3aches.p3spawner.StatesSpawn;
import org.p3aches.utils.P3Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;


public class BankItem extends Node{

    Camera camera;
    static StatesSpawn THIS_STATE = StatesSpawn.BANKING;
    NPC banker,fruitBat;
    SceneObject bankChest;
    public static boolean bank = false;
    public static boolean needSummPot = false, needPouch = false, needScroll = false;
    GroundItem[] allFruit;

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

        if(Inventory.isFull()&& !P3SVars.noteing){
            bank = true;              }

        fruitBat = Summoning.getFamiliar();
        if(fruitBat == null && Inventory.getCount(P3SVars.pouch)==0){
                needPouch = true;
                bank = true;
        }
        else{
            needPouch = false;
        }
        allFruit = GroundItems.getLoaded(PickItem.pickUpFilter);
        if( P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.SUMMONING_POTS
                && Inventory.getCount(P3SVars.SUM_POT1)==0
                && Inventory.getCount(P3SVars.SUM_POT2)==0
                && Inventory.getCount(P3SVars.SUM_POT3)==0
                && Inventory.getCount(P3SVars.SUM_POT4)==0
                && Summoning.getSpecialPoints()<7){
            needSummPot = true;
            if((Inventory.isFull() || allFruit.length==0)
                    && !P3SVars.noteing){
                bank = true;
            }
        }
        else {
            needSummPot = false;
        }
        if(Inventory.getCount(P3SVars.scroll)==0){
            needScroll = true;
            bank = true;
        }
        else{
            needScroll = false;
        }

        if(bank){
            if(Inventory.getCount(P3SVars.ALL_FRUIT)==0
                    && !needSummPot
                    && !needPouch
                    && !needScroll){
                bank = false;
                if(Bank.isOpen()){
                    Bank.close();
                    P3Util.waitFor(1,new P3Util.Cond() {
                        @Override
                        public boolean accept() {
                            Task.sleep(200, 400);
                            return (!Bank.isOpen());
                        }
                    });
                }
            }
        }

        return !(bank
                && !new Widget(11).getChild(1).visible()
                && Players.getLocal().getLocation().getLocation().distance(P3SVars.bankTile)<5 );
    }

    @Override
    public void execute() {

        banker = NPCs.getNearest("Banker");
        bankChest = SceneEntities.getNearest(P3SVars.bankChest);

        if((banker != null || bankChest != null)
                && !Bank.isOpen()){   //Open bank
            if(banker != null){
                banker.interact("Bank");
            }
            else{
                bankChest.interact("use");
            }

            P3Util.waitFor(2,new P3Util.Cond() {
                @Override
                public boolean accept() {
                    Task.sleep(100, 200);
                    return (Bank.isOpen());
                }
            });
        }
        else if((banker != null || bankChest != null)
                && Bank.isOpen()){ //Bank items

            for(final int fruit : P3SVars.ALL_FRUIT){
                if(Inventory.getCount(fruit)>0){
                    Bank.deposit(fruit, Bank.Amount.ALL);
                    Task.sleep(100,200);
                }
            }
            if(needScroll){
                if(Bank.getItemCount(true, P3SVars.scroll)>1){
                    Bank.withdraw(P3SVars.scroll, Bank.Amount.ALL_BUT_ONE);
                    P3Util.waitFor(1,new P3Util.Cond() {
                        @Override
                        public boolean accept() {
                            Task.sleep(200, 400);
                            return (Inventory.getCount(P3SVars.scroll) > 0);
                        }
                    });
                }
                else{
                    P3Spawner.curState = StatesSpawn.OUT_OF_SUPPLIES;
                }
            }
            if(needPouch){
                if(Bank.getItemCount(true, P3SVars.pouch)>1){
                    Bank.withdraw(P3SVars.pouch,1);
                    P3Util.waitFor(1,new P3Util.Cond() {
                        @Override
                        public boolean accept() {
                            Task.sleep(200, 400);
                            return (Inventory.getCount(P3SVars.pouch) == 1);
                        }
                    });
                }
                else{
                    P3Spawner.curState = StatesSpawn.OUT_OF_SUPPLIES;
                }
            }
            if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.SUMMONING_POTS){
                if(needSummPot
                    ||(Inventory.getCount(P3SVars.SUM_POT1)==0
                        && Inventory.getCount(P3SVars.SUM_POT2)==0
                        && Inventory.getCount(P3SVars.SUM_POT3)==0
                        && Inventory.getCount(P3SVars.SUM_POT4)==0)){
                    System.out.println("Bank Count: "+Bank.getItemCount(true, P3SVars.SUM_POT4));
                    if(Bank.getItemCount(true, P3SVars.SUM_POT4)>1){
                        Bank.withdraw(P3SVars.SUM_POT4,2);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {
                                Task.sleep(200, 400);
                                return (Inventory.getCount(P3SVars.SUM_POT4) == 1);
                            }
                        });
                    }
                    else{
                        P3Spawner.curState = StatesSpawn.OUT_OF_SUPPLIES;
                    }
                }
                else{
                    System.out.println("Sum pots dose left: "+P3Util.getInvDoseCnt(P3SVars.sumPots));
                    if(P3Util.getInvDoseCnt(P3SVars.sumPots)<2){
                        Bank.withdraw(P3SVars.SUM_POT4,2);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {
                                Task.sleep(200, 400);
                                return (Inventory.getCount(P3SVars.SUM_POT4) == 2);
                            }
                        });
                    }
                    if(P3Util.getInvDoseCnt(P3SVars.sumPots)<5){
                        Bank.withdraw(P3SVars.SUM_POT4,1);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {
                                Task.sleep(200, 400);
                                return (Inventory.getCount(P3SVars.SUM_POT4) == 1);
                            }
                        });
                    }

                }
            }
            if(Inventory.getCount(P3SVars.ALL_FRUIT)==0
                    && needSummPot == false
                    && needPouch == false){ //Exit contion for bank
                bank = false;
                PickItem.clearCnters();
            }
            if(bank == false){  //Close bank only when all fruit is gone
                Bank.close();
                CastScroll.castTile = P3Util.getRandTile(P3Spawner.spawnArea);
                P3Util.waitFor(1,new P3Util.Cond() {
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 400);
                        return (!Bank.isOpen());
                    }
                });
            }
        }

    }
}
