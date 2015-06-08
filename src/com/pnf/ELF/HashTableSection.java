package com.pnf.ELF;

public class HashTableSection extends Section {

    private int nbucket;
    private int nchain;
    private int[] buckets;
    private int[] chains;
    public HashTableSection(byte[] data, int s_size, int s_offset) {
        super(data, s_size, s_offset);

        nbucket = readInt(stream);
        nchain = readInt(stream);
        
        buckets = new int[nbucket];
        for(int index=0; index < nbucket; index++) 
            buckets[index] = readInt(stream);
        chains = new int[nchain];
        for(int index=0; index < nchain; index++) 
            chains[index] = readInt(stream);
    }

    public int getBucket(int index) {
         return buckets[index];
    }
    public int getChain(int index) {
        return chains[index];
    }

    public int getNBuckets() {
        return nbucket;
    }
    public int getNChains() {
        return nchain;
    }
}
