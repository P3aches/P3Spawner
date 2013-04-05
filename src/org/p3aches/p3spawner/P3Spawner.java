package org.p3aches.p3spawner;

import org.p3aches.jobs.spawner.*;
import org.p3aches.p3agile.FailSafe;
import org.p3aches.utils.P3Util;
import org.p3aches.utils.Potion;
//import org.powerbot.core.Bot;
import org.powerbot.core.Bot;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Summoning;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.methods.widget.WidgetCache;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.client.Client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@Manifest(authors = { "P3aches" }, name = "P3Spawner" ,
        description = "V1.06 Gets fruits from Fruit Bat and red eggs from Spirit Spider",
        version = 1.06,
        website = "http://www.powerbot.org/community/topic/949808-p3spawner/",
        topic = 949808)
public class P3Spawner extends ActiveScript implements PaintListener, MouseListener, KeyListener {

    double version = 1.06;
    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;
    Timer timer = new Timer(System.currentTimeMillis());
    SkillData dataXp;
    DecimalFormat df = new DecimalFormat("###,###,###.#");
    DecimalFormat df2 = new DecimalFormat("###,###,###");
    DecimalFormat df3 = new DecimalFormat("###.##");

    public static P3SpawnerForm gui;// = new P3SpawnerForm(new Frame());
    public static boolean startScript = false;
    public Rectangle paintRect = new Rectangle(480, 400, 20, 20);
    public boolean hidePaint = false;

    public static Area spawnArea = null;
    public enum RESTORE_TYPE{
        SUMMONING_POTS,OBELISK
    };
    public static RESTORE_TYPE restoreType = null;

    boolean testPaint = false, gettingImages = false;
    public static String location;
    public static int locationNumb = 0;
    public static double totalItemCnt = 0;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
    Date date;
    private Client client = Bot.client();
    long time = System.currentTimeMillis();

    BufferedImage paintImg = null;
    public static StatesSpawn curState;

    int fifteenMin =  900;
    int  lastBananasSubmit = 0, lastOrangesSubmit = 0,lastPapayasSubmit = 0,
            lastredEggsSubmit = 0,lastTotaItemsSubmit = 0;
    long lastTimeSubmit = 0;
    double lastProfitSubmit = 0;

    public void onStop(){
        try {
            URL url;
            if(PickItem.totalPrice>0){
                url = new URL("http://www.p3ach.com/p3spawnerUpdater.php?username="+ Environment.getDisplayName().replace(" ", "-")
                        + "&runtime=" + timer.getElapsed()/1000
                        + "&online=" + 0
                        +"&date="+String.valueOf(dateFormat2.format(date))
                        + "&bananas=" + PickItem.totalBanCnt
                        + "&oranges=" + PickItem.totalOrangCnt
                        + "&papayas=" + PickItem.totalPapCnt
                        + "&redEggs=" + PickItem.totalEggCnt
                        + "&itemCnt=" + (PickItem.totalBanCnt+ PickItem.totalPapCnt+ PickItem.totalOrangCnt + PickItem.totalEggCnt)
                        + "&profit=" + PickItem.totalPrice
                        + "&location="+ String.valueOf(location+locationNumb));
            }
            else{
                url = new URL("http://www.p3ach.com/p3spawnerUpdater.php?username="+ Environment.getDisplayName().replace(" ", "-")
                        +"&online=" + 0 + "&date=" + String.valueOf(dateFormat2.format(date)));
            }
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void provide(final Node... jobs) {
        for (final Node job : jobs) 	{
            if(!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public final void submit(final Job job) {
        getContainer().submit(job);
    }

    @Override
    public void onStart() {
        gettingImages = true;
        gui = new P3SpawnerForm(new Frame());
        gui.setVisible(true);
        gettingImages = false;
        while(!Game.isLoggedIn()){
            Task.sleep(200);
        }
        while(Lobby.isOpen()){
            Task.sleep(200);
        }
        while(Players.getLocal().getLocation() == null){
            Task.sleep(200);
        }
        while(Game.getClientState() != Game.INDEX_MAP_LOADED){
            Task.sleep(200);
        }
        curState = StatesSpawn.SETTING_UP;
        try {
            URL url = new URL("http://www.p3ach.com/P3Spawner.png");
            paintImg = ImageIO.read(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        while(!startScript){
             Task.sleep(200);
         }

		/*Always use*/
        provide(new FailSafe());

        /*Provide nodes here*/

        provide(new WalkToBank());
        provide(new BankItem());
        provide(new WalkBack());
        provide(new FailSafeSpawner());
        provide(new Noteing());
        provide(new PickItem());
        provide(new RestorePoints());
        provide(new SummonFam());
        provide(new CastScroll());



        curState = StatesSpawn.NONE;
        try {
            URL url;
            url = new URL("http://www.p3ach.com/p3spawnerUpdater.php?username="+ Environment.getDisplayName().replace(" ", "-")
                    + "&online=" + 1+ "&date=" + String.valueOf(dateFormat2.format(date)));
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mouse.setSpeed(Mouse.Speed.FAST);
        dataXp = new SkillData();
        if(Settings.get(94) == 33227775){ //XP pop up is on
            new WidgetChild(new Widget(548),43).click(false);//XP widget
            new Menu();
            Menu.select("Toggle XP Pop-up");
        }
        PickItem.banPrice = GeItem.lookup(P3SVars.BANNANA).getPrice() ;
        PickItem.papPrice = GeItem.lookup(P3SVars.PAPAYA).getPrice();
        PickItem.orangePrice = GeItem.lookup(P3SVars.ORANGE).getPrice();
        PickItem.eggPrice = GeItem.lookup(P3SVars.SPIDER_EGG).getPrice();
        PickItem.papCnt = Inventory.getCount(P3SVars.PAPAYA);
        PickItem.orangCnt = Inventory.getCount(P3SVars.ORANGE);
        PickItem.banCnt = Inventory.getCount(P3SVars.BANNANA);
        RestorePoints.doseCnt = P3Util.getInvDoseCnt(P3SVars.sumPots);
        RestorePoints.sumDosePrice = GeItem.lookup(P3SVars.SUM_POT4).getPrice()/4;
        CastScroll.scrollCnt = Inventory.getCount(true,P3SVars.scroll);
        CastScroll.scrollPrice = GeItem.lookup(P3SVars.scroll).getPrice();
        P3SVars.sumPots.add(new Potion(P3SVars.SUM_POT1,1));
        P3SVars.sumPots.add(new Potion(P3SVars.SUM_POT2,2));
        P3SVars.sumPots.add(new Potion(P3SVars.SUM_POT3,3));
        P3SVars.sumPots.add(new Potion(P3SVars.SUM_POT4,4));
       // Environment.enableRandom(org.powerbot.core.randoms.SpinTickets.class, false);
    }

    @Override
    public int loop() {
        if (Game.getClientState() != Game.INDEX_MAP_LOADED) {
            return 1000;
        }
        if (client != Bot.client()) {
            WidgetCache.purge();
            Bot.context().getEventManager().addListener(this);
            client = Bot.client();
        }
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
                if(P3Spawner.curState == StatesSpawn.OUT_OF_SUPPLIES)
                {
                    System.out.println("Out Of Supplies");
                    P3Util.logout();
                    shutdown();
                }
            }
        }

        if((timer.getElapsed()/1000-lastTimeSubmit)>fifteenMin){
            try {
                URL url;
                if(PickItem.totalPrice>0){
                    url = new URL("http://www.p3ach.com/p3spawnerUpdater.php?username="+ Environment.getDisplayName().replace(" ", "-")
                            + "&runtime=" + ((timer.getElapsed()/1000) - lastTimeSubmit)
                            + "&online=" + 1
                            +"&date="+String.valueOf(dateFormat2.format(date))
                            + "&bananas=" + (PickItem.totalBanCnt - lastBananasSubmit)
                            + "&oranges=" + (PickItem.totalOrangCnt - lastOrangesSubmit)
                            + "&papayas=" + (PickItem.totalPapCnt - lastPapayasSubmit)
                            + "&redEggs=" + (PickItem.totalEggCnt - lastredEggsSubmit)
                            + "&itemCnt=" + (PickItem.totalBanCnt+ PickItem.totalPapCnt+ PickItem.totalOrangCnt + PickItem.totalEggCnt - lastTotaItemsSubmit)
                            + "&profit=" + (PickItem.totalPrice-lastProfitSubmit)
                            + "&location="+ String.valueOf(location+locationNumb));

                    lastBananasSubmit =  PickItem.totalBanCnt;
                    lastOrangesSubmit =  PickItem.totalOrangCnt;
                    lastPapayasSubmit =  PickItem.totalPapCnt;
                    lastredEggsSubmit =  PickItem.totalEggCnt;
                    lastProfitSubmit =  PickItem.totalPrice;
                    lastTotaItemsSubmit =  (PickItem.totalBanCnt+ PickItem.totalPapCnt+ PickItem.totalOrangCnt + PickItem.totalEggCnt);
                    lastTimeSubmit = timer.getElapsed()/1000;
                }
                else{
                    url = new URL("http://www.p3ach.com/agilityUpdater.php?username="+ Environment.getDisplayName().replace(" ","-")
                            +"&online=" + 0+ "&date=" + String.valueOf(dateFormat2.format(date)));
                }
                URLConnection con = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Random.nextInt(10, 50);
    }


    int x, y, offsetLen;
    @Override
    public void onRepaint(Graphics g) {
        x = Mouse.getX();
        y = Mouse.getY();
        g.setColor(Color.ORANGE);

        offsetLen = 6;
        g.drawLine(x, y - offsetLen, x, y + offsetLen);
        g.drawLine(x+1, y - offsetLen,x+1, y + offsetLen);
        g.drawLine(x-1, y - offsetLen,x-1, y + offsetLen);
        g.drawLine(x - offsetLen, y ,x + offsetLen, y);
        g.drawLine(x - offsetLen, y-1 ,x + offsetLen, y-1);
        g.drawLine(x - offsetLen, y+1 ,x + offsetLen, y+1);
        if(gettingImages){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Loading Images" , 10, 421);//Lolading Images
        }
        if(!hidePaint){

            date = new Date();
            g.drawImage(paintImg, 0, 340, null);
            g.setColor(Color.CYAN);
            g.setFont(new Font("Serif", Font.BOLD, 12));
            g.drawString(Environment.getDisplayName().replace(" ", "-"), 10, 410);//Display name
            g.setFont(new Font("Serif", Font.PLAIN, 11));
            g.drawString("Special points: "+Summoning.getSpecialPoints() , 270, 430);//Soecial points
            g.setColor(Color.lightGray);
            g.setFont(new Font("Serif", Font.BOLD, 15));
            if(location != null){
                g.setFont(new Font("Serif", Font.BOLD, 14));
                g.drawString(dateFormat.format(date).toString() , 10, 426);//Set date
                g.drawString(location+"("+locationNumb+")", 375, 538);//State
                g.drawString("V"+df3.format(version), 400, 421);//version
                g.setFont(new Font("Serif", Font.BOLD, 20));

                g.drawString(timer.toElapsedString(), 176, 448);//Time Running
                g.setFont(new Font("Serif", Font.BOLD, 14));
                totalItemCnt = PickItem.totalBanCnt+ PickItem.totalEggCnt+ PickItem.totalOrangCnt+ PickItem.totalPapCnt;
                g.drawString(df2.format(totalItemCnt) , 150, 469); //total items
                g.drawString(df2.format(((totalItemCnt)/(timer.getElapsed()))*3600000) +" P/H" , 150, 482); //total items

                g.drawString(df2.format(PickItem.totalPrice) , 160, 497); //Profit/h
                g.drawString(df2.format(((PickItem.totalPrice )/(timer.getElapsed()))*3600000)+" P/H" , 160, 510); //Profit/h

                g.setFont(new Font("Serif", Font.BOLD, 16));
                g.drawString(String.valueOf(curState), 110, 538);//Current state
                g.drawString(String.valueOf(P3SVars.scrollsUsed), 426, 448); //Scrolls used
                g.drawString(String.valueOf(P3SVars.pouchesUsed), 426, 478); //laps
                if(P3SVars.pouch == P3SVars.FRUIT_POUCH)
                    g.drawString("Fruit Bat", 410, 510);
                else
                    g.drawString("Red Spider", 410, 510);
            }
            else if( curState == StatesSpawn.SETTING_UP){
                g.drawString("Setting Up", 370, 507);
            }
            else {
               //g.drawString("Start in a better spot or Turn on Fixed settings", 370, 507);

                g.setColor(Color.green);
//                for(Area a : P3SVars.A_START_AREAS){
//                    for(Tile t : a.getTileArray()){
//                        Polygon[] p =t.getBounds();
//                        if(p.length > 0){
//                            g.drawPolygon(p[0]);
//                            //g.fillPolygon(p[0]);
//                        }
//                    }
//                }
            }
        }
        else{
            g.setColor(Color.green);
            g.drawRect(paintRect.x, paintRect.y, paintRect.width, paintRect.height);
            g.drawLine(paintRect.x, paintRect.y, paintRect.x+paintRect.width, paintRect.y+paintRect.height);
            g.drawLine(paintRect.x, paintRect.y+paintRect.height, paintRect.x+paintRect.width, paintRect.y);
        }

        if(testPaint){
            g.setFont(new Font("Serif", Font.BOLD, 12));
            g.setColor(Color.black);
            g.fillRect(7, 324, 250, 64);
            if(testPaint)
                g.fillRect(7, 324, 200, 112);
            g.setColor(Color.white);
            g.drawString("Time Running "+ timer.toElapsedString()+ " $"+df2.format(((PickItem.totalPrice)/(timer.getElapsed()))*3600000)+" /hr", 10, 337);
            g.drawString("total Orange: "+ PickItem.totalOrangCnt+" Total Bananna:"+ PickItem.totalBanCnt +"total pap"+ PickItem.totalPapCnt, 10, 349);
            g.drawString("P3FruitBats:"+version +" SummPot:"+ BankItem.needSummPot+" FruitPouch: "+ BankItem.needPouch , 10, 362);
            g.drawString("State "+ curState, 10, 375);
            g.drawString("Summ Special % "+ Summoning.getSpecialPoints()+ "Summ pts: "+Summoning.getPoints() , 10, 387);

            g.drawString("Camera angle"+ Camera.getYaw() + " Pitch: " + Camera.getPitch()+"$ "+ PickItem.totalPrice , 10, 399);
            g.drawString("Scrolls left Left: " + Inventory.getCount(true, P3SVars.FRUIT_SCROLL)+" Bank:" + BankItem.bank, 10, 411);
            g.drawString("Is Moving:"+ Players.getLocal().isMoving()+"CrossHair: "+ Game.getCrossHairType(), 10, 423);
            g.drawString("Animation: "+ Players.getLocal().getAnimation()+"is inv full:  "+Inventory.isFull(), 10, 435);
            g.setColor(Color.green);

            if(spawnArea != null)
            for(Tile t : spawnArea.getTileArray()){
                Polygon[] p =t.getBounds();
                if(p.length > 0){
                    g.drawPolygon(p[0]);
                    //g.fillPolygon(p[0]);
                }
            }

            g.setColor(Color.blue);
            if(CastScroll.castTile != null){
                Polygon[] p = CastScroll.castTile.getBounds();
                //System.out.println("Bounds: "+CastScroll.castTile.getBounds());
                if(p.length > 0){
                    g.drawPolygon(p[0]);
//                    //g.fillPolygon(p[0]);
                }
            }

        }
        g.setColor(Color.green);
        if(spawnArea != null)
            for(Tile t : spawnArea.getTileArray()){
                Polygon[] p =t.getBounds();
                if(p.length > 0){
                    g.drawPolygon(p[0]);
                    //g.fillPolygon(p[0]);
                }
            }

        g.setColor(Color.blue);
        if(CastScroll.castTile != null){
            Polygon[] p = CastScroll.castTile.getBounds();
            //System.out.println("Bounds: "+CastScroll.castTile.getBounds());
            if(p.length > 0){
                g.drawPolygon(p[0]);
//                    //g.fillPolygon(p[0]);
            }
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(paintRect.contains(e.getPoint())) {
            hidePaint = !hidePaint;
        }
    }
    @Override
    public void mouseEntered(MouseEvent arg0) {

    }
    @Override
    public void mouseExited(MouseEvent arg0) {

    }
    @Override
    public void mousePressed(MouseEvent arg0) {

    }
    @Override
    public void mouseReleased(MouseEvent arg0) {

    }
    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
        Character keyPres = e.getKeyChar();
        if(keyPres.toString().equals("~")){
            testPaint = !testPaint;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}