package com.pnf.ELF;

public class HashTableSection extends Section {

    private int numBuckets;
    private int numChain;
    private int[] buckets;
    private int[] chains;
    public HashTableSection(byte[] data, int s_size, int s_offset) {
        super(data, s_size, s_offset);

        numBuckets = readInt(stream);
        numChain = readInt(stream);
        
        buckets = new int[numBuckets];
        for(int index=0; index < numBuckets; index++) 
            buckets[index] = readInt(stream);
        chains = new int[numChain];
        for(int index=0; index < numChain; index++) 
            chains[index] = readInt(stream);
    }

    public int getBucket(int index) {
         return buckets[index];
    }
    public int getChain(int index) {
        return chains[index];
    }

    public int getNBuckets() {
        return numBuckets;
    }
    public int getNChains() {
        return numChain;
    }
}
