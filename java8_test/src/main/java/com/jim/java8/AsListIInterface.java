package com.jim.java8;

import java.util.*;

/**
 * @author Jim
 * @date 2019/4/25
 */
public class AsListIInterface {

    public static void main(String[] args) {
        List<Snow> snows = Arrays.asList(new Powder(), new Light(), new Slush());

        List<Snow> snows2 = new ArrayList<>();
//        snows.add(new Powder());
        Collections.addAll(snows2, new Powder(), new Light());

        Arrays.<Snow>asList();
    }



    static class Snow {
    }

    static class Powder extends Snow {
    }

    static class Light extends Powder {
    }

    static class Heavy extends Powder {
    }

    static class Crusty extends Snow {
    }

    static class Slush extends Snow {
    }
}
