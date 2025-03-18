public enum Kl {

    SETOSA,
    VERSICOLOR,
    VIRGINICA,
    UNDETERMINED;

    public static final Kl[] irisTypes = { SETOSA, VERSICOLOR, VIRGINICA };

   public static String getOthers(Kl kl) {
       return switch (kl) {
           case VERSICOLOR -> "[SETOSA/VIRGINICA]";
           case VIRGINICA -> "[SETOSA/VERSICOLOR]";
           case SETOSA -> "[VERSICOLOR/VIRGINICA]";
           default -> throw new IllegalArgumentException("Unknown Kl");
       };
   }

}
