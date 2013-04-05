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
public class WalkToBank extends Node {
    static StatesSpawn THIS_STATE = StatesSpawn.WALK_BACK_TO_BANK;
    Item summPot;
    SceneObject obliskSO;
    GroundItem[]  allFruit;
    Widget depositBox ;

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
        depositBox = new Widget(11);

        return !(Players.getLocal().getLocation().getLocation().distance(P3SVars.bankTile) > 3
        && BankItem.bank == true
        && !depositBox.getChild(1).visible());
    }

    @Override
    public void execute() {
        if(Players.getLocal().getLocation().getLocation().distance(P3SVars.bankTile) < 14){
            if(!P3SVars.bankTile.isOnScreen()){
                P3SVars.bankTile.clickOnMap();
                P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 300);
                        return (-1 != Players.getLocal().getAnimation()
                                || Players.getLocal().getLocation().distance(P3SVars.bankTile) <3);
                    }
                });
            }else{
                P3SVars.bankTile.interact("Walk here");
                System.out.println("Walk back to bank");
                P3Util.waitFor(1,new P3Util.Cond() {  //Wait if not idle
                    @Override
                    public boolean accept() {
                        Task.sleep(200, 300);
                        return (-1 != Players.getLocal().getAnimation()
                                || Players.getLocal().getLocation().distance(P3SVars.bankTile) <3);
                    }
                });
            }
        }
        else if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.OBELISK){
            P3Util.walkPath(P3SVars.toObelisk,true,25);
        }
    }
}
