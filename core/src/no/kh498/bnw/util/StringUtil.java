package no.kh498.bnw.util;

/**
 * @author karl henrik
 */
@SuppressWarnings("NonJREEmulationClassesInClientCode")
public final class StringUtil {

    public static String toTitleCase(final String input) {
        final StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            }
            else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
