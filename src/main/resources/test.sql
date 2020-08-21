SELECT
MD5(CONCAT(ab.user_id,ab.order_id,ab.contract_id,ab.cycle_start_time)) as id,
ab.user_id userId,
uoc.alias userName,
CONCAT_WS( '#', pcc.CODE, pcc.NAME ) cloudCenter,
ab.contract_id contractId,
bci.crm_contract_code crmContractId,
bci.contract_code excelContractId,
CONCAT_WS( '#', cci.company_code, cci.company_name ) signCompany,
DATE_FORMAT( ab.cycle_start_time, '%Y%m' ) MONTH,
SUM( ab.original_price ) AS amount,
ab.order_id orderDetailId,
ab.instance_id instanceId,
plt.parent_code pLineParent,
CONCAT_WS( '#', pl.line_type_code, plt.NAME ) pLineType,
CONCAT_WS( '#', ab.product_line, pl.NAME ) productLine,
CONCAT_WS( '#', ab.product_type, pt.NAME ) productType,
CONCAT_WS( '#', ab.region, pr.NAME ) region,
ab.available_zone availableZone,
ab.product_config productConfig,
main.order_id as orderId,
main.paper_order_id as paperOrderId
FROM
	account_bill as ab
	LEFT JOIN user_organ_collect uoc ON ( ab.user_id = uoc.user_id AND uoc.center_id = ab.center_id )
	LEFT JOIN busiopt_contract_info bci ON ab.contract_id = bci.contract_id
	LEFT JOIN contract_company_info cci ON bci.company_code = cci.company_code
	LEFT JOIN product_cloud_center pcc ON ab.center_id = pcc.id
	LEFT JOIN product_line pl ON pl.CODE = ab.product_line
	LEFT JOIN product_line_type plt ON plt.CODE = pl.line_type_code
	LEFT JOIN product_type pt ON ab.product_type = pt.CODE 
	LEFT JOIN product_region pr ON ab.region = pr.CODE 
    LEFT JOIN order_detail_new detail ON ab.order_id = detail.sub_order_id and ab.center_id = detail.center_id
    LEFT JOIN order_main_new main ON main.order_id = detail.order_id and main.center_id = detail.center_id
	WHERE
	LEFT ( ab.cycle_start_time, 7 ) = '2020-07'
	AND ab.contract_id IS NOT NULL 
	AND ab.deliver_type = "formal" 
	AND ab.center_id = 183
GROUP BY
    ab.center_id,
	DATE_FORMAT( ab.cycle_start_time, '%Y%m' ),
	ab.user_id,
	ab.order_id,
	ab.contract_id 
ORDER BY
	ab.created_time DESC;
