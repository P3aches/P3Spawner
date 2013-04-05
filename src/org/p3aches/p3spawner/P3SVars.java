package org.p3aches.p3spawner;

import org.p3aches.utils.Potion;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

import java.util.ArrayList;

public class P3SVars {
    /*Generic*/
    public static Tile[]toObelisk = null;
    public static int obelisk = -1;
    public static Tile bankTile = null;
    public static int pouch = -1;
    public static int scroll = -1;
    public static int scrollsUsed = 0;
    public static int pouchesUsed = 0;
    public static int bankChest = 0;
    public static boolean noteing = false;
    public static int npcLepercon = 0;

    /*NPCs*/
    public static int NPC_LEP_CATH = 3021;

	/*Tiles*/

    public static final Tile T_BANK_EDVIL = new Tile(3093,3495,0);
    public static final Tile T_BANK_EDVIL_NORTH = new Tile(3097,3496,0);
    public static final Tile T_BANK_EDVIL_SOUTH = new Tile(3094,3491,0);
    public static final Tile T_SPAWN_EDVIL1 = new Tile(3086,3496,0);
    public static final Tile T_BANK_CWARS = new Tile(2444,3083,0);
    public static final Tile T_BANK_CATHEBY = new Tile(2810,3441,0);
    public static final Tile T_BANK_LLETYA = new Tile(2352,3164,0);

    /*Settings*/
	public static final int SET_LOW_XP = 91;

	/*Scene Entities*/
	public static final int S_EDVIL_OBELISK = 29992;
    public static final int S_CWARS_OBELISK = 29992;
    public static final int S_CATHERBY_OBELISK = 29992;
    public static final int S_C_WARS_BANK = 4483;

    /*Areas*/
		/*edvil*/
    public static final Area A_EDV_WEST = new Area(new Tile(3085,3498,0),new Tile(3089,3491,0));
    public static final Area A_EDV_1 = new Area(new Tile(3085,3491,0),new Tile(3090,3488,0));
    public static final Area A_EDV_2 = new Area(new Tile(3091,3488,0),new Tile(3095,3485,0));
    public static final Area A_EDV_3= new Area(new Tile(3099,3497,0),new Tile(3102,3494,0));
    public static final Area A_EDV_4= new Area(new Tile(3099,3500,0),new Tile(3103,3496,0));
    public static final Area A_EDV_5 = new Area(new Tile(3090,3503,0),new Tile(3094,3500,0));
    public static final Area A_EDV_1_NO_SUM = new Area(new Tile(3127,3518,0),new Tile(3130,3516,0));
    public static final Area A_EDV_2_NO_SUM = new Area(new Tile(3126,3517,0),new Tile(3128,3514,0));
        /*cwars*/
    public static final Area A_CWARS_1 = new Area(new Tile(2447, 3091, 0), new Tile(2449, 3089, 0) );
    public static final Area A_CWARS_1_NO_SUM = new Area(new Tile(2461, 3089, 0), new Tile(2463, 3086, 0) );
        /*Catherby*/
    public static final Area A_CATHERBY_1_NO_SUM = new Area(new Tile(2852, 3439, 0), new Tile(2855, 3437, 0) );
    public static final Area A_CATHERBY_2_NO_SUM = new Area(new Tile(2856, 3433, 0), new Tile(2859, 3430, 0) );
    public static final Area A_CATHERBY_1 = new Area(new Tile(2808, 3438, 0), new Tile(2810, 3436, 0) );
    public static final Area A_CATHERBY_2 = new Area(new Tile(2813, 3441, 0), new Tile(2815, 3439, 0) );
        /*Lletya*/
    public static final Area A_LLETYA_1 = new Area(new Tile(2353, 3155, 0), new Tile(2356, 3153, 0) );
    /*Path*/
    public static Tile[] Path_edvilObelisk = new Tile[] { new Tile(3096, 3497, 0), new Tile(3104, 3500, 0), new Tile(3110, 3506, 0),
            new Tile(3118, 3509, 0), new Tile(3124, 3515, 0), new Tile(3127, 3516, 0) };
    public static Tile[] Path_cWarsObelisk = new Tile[] { new Tile(2443, 3085, 0), new Tile(2450, 3089, 0), new Tile(2458, 3088, 0),
            new Tile(2461, 3085, 0) };
    public static Tile[] Path_catherbyObelisk1 = new Tile[] { new Tile(2808, 3439, 0), new Tile(2808, 3435, 0), new Tile(2816, 3435, 0),
            new Tile(2824, 3436, 0), new Tile(2832, 3435, 0), new Tile(2840, 3434, 0),
            new Tile(2848, 3436, 0), new Tile(2850, 3436, 0) };
    public static Tile[] Path_catherbyObelisk2 = new Tile[] { new Tile(2808, 3439, 0), new Tile(2808, 3435, 0), new Tile(2816, 3435, 0),
            new Tile(2824, 3436, 0), new Tile(2832, 3435, 0), new Tile(2840, 3434, 0),
            new Tile(2848, 3436, 0), new Tile(2856, 3431, 0) };
    public static Tile[] Path_shiloVillageObelisk = new Tile[] { new Tile(2851, 2954, 0), new Tile(2851, 2959, 0), new Tile(2846, 2961, 0),
            new Tile(2838, 2965, 0), new Tile(2830, 2968, 0), new Tile(2822, 2967, 0) };
    public static Tile[] Path_DraynorVillage = new Tile[] { new Tile(3092, 3244, 0), new Tile(3092, 3248, 0), new Tile(3097, 3248, 0),
            new Tile(3099, 3243, 0), new Tile(3099, 3240, 0), new Tile(3099, 3232, 0),
            new Tile(3097, 3227, 0), new Tile(3094, 3222, 0) };
    public static Tile[] Path_Canifis = new Tile[] { new Tile(3508, 3480, 0), new Tile(3500, 3482, 0), new Tile(3492, 3484, 0),
            new Tile(3485, 3480, 0), new Tile(3477, 3476, 0), new Tile(3469, 3475, 0),
            new Tile(3461, 3477, 0), new Tile(3455, 3484, 0), new Tile(3452, 3487, 0) };
    public static Tile[] Path_Yanille = new Tile[] { new Tile(2610, 3094, 0), new Tile(2606, 3099, 0), new Tile(2618, 3108, 0),
            new Tile(2617, 3116, 0), new Tile(2617, 3124, 0), new Tile(2621, 3131, 0),
            new Tile(2623, 3139, 0), new Tile(2625, 3147, 0), new Tile(2625, 3153, 0) };
    public static Tile[] Path_LunarIsle = new Tile[] { new Tile(2099, 3918, 0), new Tile(2099, 3915, 0), new Tile(2104, 3915, 0),
            new Tile(2110, 3915, 0) };
    public static Tile[] Path_Neitiznot = new Tile[] { new Tile(2336, 3806, 0), new Tile(2328, 3804, 0), new Tile(2320, 3803, 0),
            new Tile(2317, 3803, 0) };

	/*Items*/
	public static final int FRUIT_POUCH = 12033;
    public static final int FRUIT_SCROLL = 12423;
    public static final int SPIDER_POUCH = 12059;
    public static final int SPIDER_SCROLL = 12428;

    public static final int PAPAYA = 5972;
    public static final int ORANGE = 2108;
    public static final int BANNANA = 1963;
    public static final int COCONUT = 5974;
    public static final int STRAWBERRY = 5504;
    public static final int LEMMON = 2102;
    public static final int LIME = 2120;
    public static final int PINEAPPLE = 2114;
    public static final int STRABERRY_SEEDS = 5323;
    public static final int WATERMELLON_SEEDS = 5321;
    public static final int WATERMELLON = 5982;
    public static final int EMPTY_VIAL = 229;
    public static final int SPIDER_EGG = 223;
    public static final int FLAX = 1779;

    public static final int[]ALL_FRUIT = {PAPAYA,ORANGE,BANNANA,COCONUT,STRAWBERRY,LEMMON,LIME,
            PINEAPPLE,STRABERRY_SEEDS,WATERMELLON_SEEDS,WATERMELLON,EMPTY_VIAL,SPIDER_EGG,FLAX};
    public static final int[]NOTE_ALL_FRUIT = {PAPAYA,ORANGE,BANNANA,COCONUT,STRAWBERRY,LEMMON,LIME,
            PINEAPPLE,WATERMELLON};

    public static final int SUM_POT4 = 12140;
    public static final int SUM_POT3 = 12142;
    public static final int SUM_POT2 = 12144;
    public static final int SUM_POT1 = 12146;
    public static final ArrayList<Potion> sumPots = new ArrayList<Potion>();
    public static final int[]SUMM_POT = {SUM_POT4,SUM_POT3,SUM_POT2,SUM_POT1};

}
