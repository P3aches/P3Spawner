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
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.widget.ChatOptions;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.ChatOption;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: C0r31N
 * Date: 3/11/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class FailSafeSpawner extends Node {
    static StatesSpawn THIS_STATE = StatesSpawn.FAIL_SAFE;
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

        return !(new Widget(1188).getChild(4).visible()
                || new Widget(1184).getChild(0).visible()
                || new Widget(11).getChild(1).visible());
    }

    @Override
    public void execute() {
        if(new Widget(11).getChild(1).visible()){
            new Widget(11).getChild(15).click(true);
            System.out.println("Exit Deposit box");
        }
        Players.getLocal().getLocation().interact("Walk Here");
        P3Util.waitFor(1,new P3Util.Cond() {
            @Override
            public boolean accept() {
                return !Menu.isOpen();
            }
        });
    }
}
