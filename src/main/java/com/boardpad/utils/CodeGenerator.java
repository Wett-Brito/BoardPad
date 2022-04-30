package com.boardpad.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class CodeGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private boolean useLower;
    private boolean useUpper;
    private boolean useDigits;

    private CodeGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    private CodeGenerator(codeGeneratorBuilder builder) {
        this.useLower = builder.useLower;
        this.useUpper = builder.useUpper;
        this.useDigits = builder.useDigits;
    }

    public static class codeGeneratorBuilder {

        private boolean useLower;
        private boolean useUpper;
        private boolean useDigits;

        public codeGeneratorBuilder() {
            this.useLower = false;
            this.useUpper = false;
            this.useDigits = false;
        }

        /**
         * Set true in case you would like to include lower characters
         * (abc...xyz). Default false.
         *
         * @param useLower true in case you would like to include lower
         * characters (abc...xyz). Default false.
         * @return the builder for chaining.
         */
        public codeGeneratorBuilder useLower(boolean useLower) {
            this.useLower = useLower;
            return this;
        }

        /**
         * Set true in case you would like to include upper characters
         * (ABC...XYZ). Default false.
         *
         * @param useUpper true in case you would like to include upper
         * characters (ABC...XYZ). Default false.
         * @return the builder for chaining.
         */
        public codeGeneratorBuilder useUpper(boolean useUpper) {
            this.useUpper = useUpper;
            return this;
        }

        /**
         * Set true in case you would like to include digit characters (123..).
         * Default false.
         *
         * @param useDigits true in case you would like to include digit
         * characters (123..). Default false.
         * @return the builder for chaining.
         */
        public codeGeneratorBuilder useDigits(boolean useDigits) {
            this.useDigits = useDigits;
            return this;
        }

        /**
         * Get an object to use.
         *
         * @return the {@link gr.idrymavmela.business.lib.codeGenerator}
         * object.
         */
        public CodeGenerator build() {
            return new CodeGenerator(this);
        }
    }

    /**
     * This method will generate a code depending the use* properties you
     * define. It will use the categories with a probability. It is not sure
     * that all of the defined categories will be used.
     *
     * @param length the length of the code you would like to generate.
     * @return a code that uses the categories you define when constructing
     * the object with a probability.
     */
    public String generate(int length) {
        // Argument Validation.
        if (length <= 0) {
            return "";
        }

        // Variables.
        StringBuilder code = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        // Collect the categories to use.
        List<String> charCategories = new ArrayList<>(3);
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }

        // Build the code.
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            code.append(charCategory.charAt(position));
        }
        return new String(code);
    }
}