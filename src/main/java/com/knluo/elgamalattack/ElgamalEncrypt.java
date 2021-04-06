package com.knluo.elgamalattack;

import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Logger;

public class ElgamalEncrypt {
    public BigInteger largeProbablePrime() {
        BigInteger p;
        do {
            p = BigInteger.probablePrime(100, new Random());
        } while (p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)).isProbablePrime(100));

        BigInteger t = primitiveRoot(p);
        System.out.println(p);
        System.out.println(t);
        return p;
    }

    public static void main(String[] args) {
        ElgamalEncrypt elgamalEncrypt = new ElgamalEncrypt();
        elgamalEncrypt.generate();
    }

    private BigInteger primitiveRoot(BigInteger p) {
        BigInteger q = p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
        BigInteger t;
        do{
            t = randomBigInteger(BigInteger.ONE,p.subtract(BigInteger.ONE));
        }while(t.modPow(q,p).compareTo(BigInteger.ONE) == 0 || t.modPow(BigInteger.valueOf(2),p).compareTo(BigInteger.ONE) == 0);

        return t;

    }

    private BigInteger randomBigInteger(BigInteger start,BigInteger end){
        BigInteger random;
        do{
            random = new BigInteger(100, new Random());
        }while(random.compareTo(start)<0 || random.compareTo(end)>0);

        return random;
    }

    private BigInteger[] generateKey(BigInteger p, BigInteger t){
        BigInteger[] keys = new BigInteger[2];
        BigInteger privateKey = randomBigInteger(BigInteger.ONE,p.subtract(BigInteger.ONE));
        BigInteger publicKey = t.modPow(privateKey,p);

        keys[0] = privateKey;
        keys[1] = publicKey;

        System.out.println("privateKey:"+privateKey);
        System.out.println("publicKey:"+publicKey);

        return keys;
    }

    boolean isOrigin(BigInteger a, BigInteger m) {
        if (a.gcd(m).intValue() != 1) return false;
        BigInteger i = BigInteger.valueOf(2);
        while (i.compareTo(m.subtract(BigInteger.ONE)) <0) {
            if (m.mod(i).intValue() == 0) {
                if (a.modPow(i, m).intValue() == 1)
                    return false;
                while (m.mod(i).intValue() == 0)
                    m = m.divide(i);
            }
            i = i.add(BigInteger.ONE);
        }
        return true;
    }

    public void generate(){
        BigInteger p = largeProbablePrime();
        BigInteger t = primitiveRoot(p);
        generateKey(p,t);
    }
}
