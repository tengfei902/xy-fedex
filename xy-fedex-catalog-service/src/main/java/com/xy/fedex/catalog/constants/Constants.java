package com.xy.fedex.catalog.constants;

import java.util.regex.Pattern;

public class Constants {

    public static final String DERIVE_FORMULA_REGEX = "\\$\\{(.+?)\\}";

    public static final Pattern DERIVE_FORMULA_PATTERN = Pattern.compile(Constants.DERIVE_FORMULA_REGEX);

    public static final String RELATE_MODEL_IDS = "relate_model_ids";

    public static final String APP_RELATE_MODELS = "app.relate_models";

    public static final String MODEL_FORCE_DIMS = "model.force_dims";

    public static final String MODEL_GROUPING_SET = "model.grouping_set";
}
