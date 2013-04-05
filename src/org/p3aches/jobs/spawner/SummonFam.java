package org.p3aches.jobs.spawner;


import org.p3aches.p3agile.P3AVars;
import org.p3aches.p3agile.States;
import org.p3aches.p3spawner.P3SVars;
import org.p3aches.p3spawner.P3Spawner;
import org.p3aches.p3spawner.StatesSpawn;
import org.p3aches.utils.P3Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class SummonFam extends Node{

	Camera camera;
	SceneObject obelisk;
    Item summPot;
	static StatesSpawn THIS_STATE = StatesSpawn.SUMMON_FRUIT_POUCH;
    NPC familiar;
    boolean notDone;

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
        notDone = false;
        familiar = Summoning.getFamiliar();
        if((familiar == null || Summoning.getTimeLeft()< 120)){
            if(Inventory.getCount(P3SVars.pouch)>0
                    && Summoning.getPoints()>=7){
                notDone = true;
            }
        }
		return !(notDone);
	}

	@Override
	public void execute() {
        if(Summoning.getPoints()>=7){
            if(P3SVars.pouch == P3SVars.FRUIT_POUCH){
                Summoning.summonFamiliar(Summoning.Familiar.FRUIT_BAT);
            }
            else{
                Summoning.summonFamiliar(Summoning.Familiar.SPIRIT_SPIDER);
            }
            P3SVars.pouchesUsed++;
            P3Util.waitFor(3,new P3Util.Cond() {
                @Override
                public boolean accept() {
                    Task.sleep(300,500);
                    familiar = Summoning.getFamiliar();
                    return (familiar != null);
                }
            });
            BankItem.needPouch = false;
        }
//        else{ //Out of points Drink potion or use Obelisk
//            if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.SUMMONING_POTS){
//                summPot = Inventory.getItem(P3SVars.SUMM_POT);
//                if(summPot != null){
//                    summPot.getWidgetChild().interact("Drink");
//                    P3Util.waitFor(1,new P3Util.Cond() {
//                        @Override
//                        public boolean accept() {
//                            Task.sleep(200, 400);
//                            return (Summoning.getPoints() > 7);
//                        }
//                    });
//                }
//            }
//            else if(P3Spawner.restoreType == P3Spawner.RESTORE_TYPE.OBELISK){
//
//            }
//        }
    }
}
