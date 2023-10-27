package com.xy.fedex.catalog.constants;

import java.util.regex.Pattern;

public class Constants {

    public static final String DERIVE_FORMULA_REGEX = "\\$\\{(.+?)\\}";

    public static final Pattern DERIVE_FORMULA_PATTERN = Pattern.compile(Constants.DERIVE_FORMULA_REGEX);
}
