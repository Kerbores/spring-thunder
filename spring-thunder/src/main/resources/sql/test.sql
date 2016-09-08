/*
test
*/
SELECT
	p.*, CASE sup.id IS NULL
WHEN 1 THEN
	''
ELSE
	'selected'
END AS has_permission
FROM
	t_permission p
LEFT JOIN (
	SELECT
		*
	FROM
		t_user_permission
	WHERE
		u_id = @id
) sup ON p.id = sup.p_id