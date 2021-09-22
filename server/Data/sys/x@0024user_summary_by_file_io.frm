TYPE=VIEW
query=select if(isnull(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`),\'background\',`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`) AS `user`,sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`COUNT_STAR`) AS `ios`,sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`SUM_TIMER_WAIT`) AS `io_latency` from `performance_schema`.`events_waits_summary_by_user_by_event_name` group by if(isnull(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`),\'background\',`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`) order by sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`SUM_TIMER_WAIT`) desc
md5=dbf17efa1265950daf8d5b1b934a5fa6
updatable=0
algorithm=1
definer_user=root
definer_host=localhost
suid=0
with_check_option=0
timestamp=2021-08-31 13:30:15
create-version=1
source=SELECT IF(user IS NULL, \'background\', user) AS user, SUM(count_star) AS ios, SUM(sum_timer_wait) AS io_latency  FROM performance_schema.events_waits_summary_by_user_by_event_name GROUP BY IF(user IS NULL, \'background\', user) ORDER BY SUM(sum_timer_wait) DESC
client_cs_name=utf8
connection_cl_name=utf8_general_ci
view_body_utf8=select if(isnull(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`),\'background\',`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`) AS `user`,sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`COUNT_STAR`) AS `ios`,sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`SUM_TIMER_WAIT`) AS `io_latency` from `performance_schema`.`events_waits_summary_by_user_by_event_name` group by if(isnull(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`),\'background\',`performance_schema`.`events_waits_summary_by_user_by_event_name`.`USER`) order by sum(`performance_schema`.`events_waits_summary_by_user_by_event_name`.`SUM_TIMER_WAIT`) desc