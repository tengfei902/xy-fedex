create table biz_line_0.ecs (
    dt string comment '日期',
    shop_id bigint comment '门店id',
    shop_name string comment '门店名',
    shop_full_name string comment '门店全名',
    tenant_id bigint comment '租户ID',
    tenant_name string comment '租户名',
    brand_id bigint comment '品牌id',
    brand_name string comment '品牌名',
    sale_amt decimal(38,6) comment '销售额',
    sale_cnt int comment '销量',
    refund_amt decimal(38,6) comment '退款额',
    refund_cnt int comment '退款量',
    refund_amt_rate decimal(38,6) comment '退款率',
    refund_cnt_rate decimal(38,6) comment '退货率',
    expose_pv bigint comment '曝光pv',
    expose_uv bigint comment '曝光uv',
    click_pv bigint comment '点击pv',
    click_uv bigint comment '点击uv',
    view_pv bigint comment '浏览pv',
    view_uv bigint comment '浏览uv',
    visit_buy_rate decimal(38,6) comment '访购率'
) tblproperties(
  'relate_model_ids' = '23,24,25'
) comment '电商demo数据demo';

