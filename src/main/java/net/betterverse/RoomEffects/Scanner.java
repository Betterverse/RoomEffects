package net.betterverse.RoomEffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Scanner {
    
    //Blocks to scan
    protected HashMap<Material, Integer> blocks = new HashMap<Material, Integer>();
    protected int limit;
    
    public boolean checkRoom(Block start) {
        List<Block> initial = new ArrayList<Block>();
        int startx = start.getX() - limit, starty = start.getY() - limit, startz = start.getZ() - limit;
        int endx = start.getX() + limit, endy = start.getY() + limit, endz = start.getZ() + limit;
        
        //Populate initial list of blocks, populate amount hashmap
        HashMap<Material, Integer> temp = new HashMap<Material, Integer>();
        for (int xop = startx; xop <= endx; xop++) {
            for (int yop = starty; yop <= endy; yop++) {
                for (int zop = startz; zop <= endz; zop++) {
                    Block b = start.getWorld().getBlockAt(xop, yop, zop);
                    if (blocks.containsKey(b.getType())) {
                        if (!temp.containsKey(b.getType())) {
                            temp.put(b.getType(), 1);
                        } else {
                            if (temp.get(b.getType()) >= blocks.get(b.getType())) continue;
                            int old = temp.get(b.getType());
                            old += 1;
                            temp.put(b.getType(), old);
                        }
                        initial.add(b);
                    }
                }
            }
        }
        
        //Check that the amount of each block type found is correct.
        for (Material mat : blocks.keySet()) {
            if (!temp.containsKey(mat) || temp.get(mat) < blocks.get(mat)) {
                return false;
            }
        }

        return hasBlocks(initial);
    }
    
    private boolean hasBlocks(List<Block> blocklist) {
        for (Block start : blocklist) {
            List<Block> found = new ArrayList<Block>();
            int startx = start.getX() - limit, starty = start.getY() - limit, startz = start.getZ() - limit;
            int endx = start.getX() + limit, endy = start.getY() + limit, endz = start.getZ() + limit;

            //Run same check pattern on each of the original blocks.
            //Only add blocks that were found the first time into our new list.
            for (int xop = startx; xop <= endx; xop++) {
                for (int yop = starty; yop <= endy; yop++) {
                    for (int zop = startz; zop <= endz; zop++) {
                        Block b = start.getWorld().getBlockAt(xop, yop, zop);
                        if (blocklist.contains(b)) {
                            found.add(b);
                        }
                    }
                }
            }
            
            //As no new blocks could have been added, we only need to see if sizes match
            if (found.size() != blocklist.size()) return false;
        }
        return true;
    }
    
}
