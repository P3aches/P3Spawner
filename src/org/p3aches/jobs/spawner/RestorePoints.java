package org.p3aches.jobs.spawner;

import org.p3aches.p3agile.P3AVars;
import org.p3aches.p3agile.States;
import org.p3aches.p3spawner.P3SVars;
import org.p3aches.p3spawner.P3Spawner;
import org.p3aches.p3spawner.StatesSpawn;
import org.p3aches.utils.P3Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: C0r31N
 * Date: 3/11/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestorePoints extends Node {
    static StatesSpawn THIS_STATE = StatesSpawn.RESTORE_POINTS;
    Item summPot;
    SceneObject obliskSO;
    GroundItem[]  allFruit;
    public static int doseCnt = 0, sumDosePrice = 0;

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
//        System.out.println("Enter restore: "+(Summoning.getSpecialPoints()<=7
//                && BankItem.bank == false));
        if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.SUMMONING_POTS){
            return !((Summoning.getSpecialPoints()<7||(Summoning.getPoints()<= 7))
                    && BankItem.bank == false);
        }
        else{
            return !((Summoning.getPoints()< 7)
                    && BankItem.bank == false);
        }
    }

    @Override
    public void execute() {
        if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.SUMMONING_POTS){
            summPot = Inventory.getItem(PickItem.sumFilter);
            if(summPot != null){
                summPot.getWidgetChild().interact("Drink");
//                System.out.println("Mouse: "+ Game.getCrossHairType());
                P3Util.waitFor(1,new P3Util.Cond() {
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 400);
//                        System.out.println("After Mouse: " + Game.getCrossHairType());
                        return (Summoning.getSpecialPoints() > 7);
                    }
                });
                if(P3Util.getInvDoseCnt(P3SVars.sumPots) != doseCnt){
                    doseCnt = Inventory.getCount(P3SVars.SPIDER_EGG);
                    PickItem.totalPrice-= sumDosePrice;
                }
            }
            else{
                BankItem.bank = true;
            }
        }
        else if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.OBELISK
                && Summoning.getPoints()< 7){
            obliskSO = SceneEntities.getNearest(P3SVars.obelisk);
            if(!obliskSO.isOnScreen()){
                obliskSO.getLocation().clickOnMap();
                Task.sleep(100,200);
                P3Util.turnTo(obliskSO,new Camera());
            }
            obliskSO.interact("Renew Points");
            P3Util.waitFor(3,new P3Util.Cond() {
                @Override
                public boolean accept() {
                    Task.sleep(200,300);
                    return (Summoning.getPoints() > 7);
                }
            });

        }
    }
}
