select
  l_orderkey,
  '##' as col1
from
  lineitem
  join orders on l_orderkey = o_orderkey
group by
  l_orderkey
order by
  l_orderkey;