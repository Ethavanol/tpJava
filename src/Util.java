import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static float lerp(float a, float b, float t) {
        return a*(1 - t) + b * t;
    }

    public static float easeInSine(float t){
        return 1 - (float) Math.cos((t * Math.PI) / 2);
    }

    public static int getRandomNegativeOrPositiveSigne(){
        return ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
    }
}
