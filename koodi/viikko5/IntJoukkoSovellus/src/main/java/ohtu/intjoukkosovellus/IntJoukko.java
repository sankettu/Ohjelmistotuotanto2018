package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KOKO = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this.alustaTaulukko(KOKO);
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int koko) {
        if (koko < 0) {
            return;
        }
        this.alustaTaulukko(koko);
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    public IntJoukko(int koko, int kasvatuskoko) {
        if (koko < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti ja kasvatuskoko oltava positiivisia");//heitin vaan jotain :D
        }
        this.alustaTaulukko(koko);
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            taulukko[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm % taulukko.length == 0) {
                this.kasvataTaulukkoaKasvatuskoolla();
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int kohta = this.etsiLuvunKohtaTaulukosta(luku);
        if (kohta != -1) {
            this.siirraTaulukonLoppuosaaYhdenTaakseKohdanJalkeen(kohta);
            alkioidenLkm--;
            return true;
        }

        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + taulukko[0] + "}";
        } else {
            return(this.toStringKunYli1Alkiota());
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = taulukko[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }
        return z;
    }

    private int[] alustaTaulukko(int koko) {
        taulukko = new int[koko];
        for (int i = 0; i < taulukko.length; i++) {
            taulukko[i] = 0;
        }
        return taulukko;

    }

    private void kasvataTaulukkoaKasvatuskoolla() {
        int[] taulukkoOld = new int[taulukko.length];
        taulukkoOld = taulukko;
        kopioiTaulukko(taulukko, taulukkoOld);
        taulukko = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, taulukko);
    }

    private int etsiLuvunKohtaTaulukosta(int luku) {
        int kohta = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                kohta = i; //siis luku löytyy tuosta kohdasta :D
                taulukko[kohta] = 0;
                break;
            }
        }
        return kohta;
    }

    private void siirraTaulukonLoppuosaaYhdenTaakseKohdanJalkeen(int kohta) {
        int apu;
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            apu = taulukko[j];
            taulukko[j] = taulukko[j + 1];
            taulukko[j + 1] = apu;
        }
    }

    private String toStringKunYli1Alkiota() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += taulukko[i];
            tuotos += ", ";
        }
        tuotos += taulukko[alkioidenLkm - 1];
        tuotos += "}";
        return tuotos;
    }
}
