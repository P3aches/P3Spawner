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
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: C0r31N
 * Date: 3/11/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Noteing extends Node {
    static StatesSpawn THIS_STATE = StatesSpawn.NOTEING;
    Item I_fruit;
    NPC lepercon = null;
    SceneObject obliskSO;
    Camera camera;
    int invSize = 0;

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
        //System.out.println("Enter Noteing: "+(Summoning.getSpecialPoints()<=7
//                && BankItem.bank == false));
        if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.OBELISK){
            return !(P3SVars.noteing
                    && BankItem.bank == false
                    && Inventory.isFull()
                    && P3SVars.pouch == P3SVars.FRUIT_POUCH);
        }
        else{
            return true;
        }
    }

    @Override
    public void execute() {
        lepercon = NPCs.getNearest(P3SVars.npcLepercon);
        if(lepercon != null) {
            if(!lepercon.isOnScreen()){
                lepercon.getLocation().clickOnMap();
                Task.sleep(100,200);
                P3Util.turnTo(lepercon,camera);
            }
            for(final int fruit : P3SVars.NOTE_ALL_FRUIT){
                while(Inventory.getCount(fruit)>0){
                    invSize = Inventory.getCount();
                    if(fruit == P3SVars.LEMMON||fruit == P3SVars.LIME)
                    {
                        I_fruit = Inventory.getItem(fruit);
                        I_fruit.getWidgetChild().interact("Drop");
                        Task.sleep(100,200);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {
                                return invSize != Inventory.getCount();
                            }
                        });
                    }
                    else{
                        Inventory.selectItem(fruit);
                        Task.sleep(100,200);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {
                                if(Inventory.getSelectedItem()!= null)
                                    return Inventory.getSelectedItem().getId() == fruit;
                                else
                                    return true;
                            }
                        });
                    }

                    if(fruit != P3SVars.LEMMON
                            && fruit != P3SVars.LIME
                            && Inventory.getSelectedItem()!= null)
                    {
                        lepercon.interact("Use");
                        Task.sleep(100,200);
                        P3Util.waitFor(1,new P3Util.Cond() {
                            @Override
                            public boolean accept() {

                                return invSize != Inventory.getCount();
                            }
                        });
                    }

                }
            }
        }
        else{
            P3Util.walkPath(P3SVars.Path_catherbyObelisk2,false,6);
        }

    }
}
