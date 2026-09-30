-- F7/T02 同步验证固件：最小 p_project（列对齐同步 SELECT）。
CREATE TABLE IF NOT EXISTS p_project (
  id BIGINT PRIMARY KEY,
  code VARCHAR(64),
  name VARCHAR(128),
  abbreviation VARCHAR(128),
  status VARCHAR(16),
  type VARCHAR(16),
  customer_type VARCHAR(32),
  contract_id BIGINT,
  manager_id VARCHAR(64),
  start_time DATETIME,
  end_time DATETIME,
  del_flag VARCHAR(1) DEFAULT '0'
);
INSERT INTO p_project
  (id, code, name, abbreviation, status, type, customer_type, contract_id,
   manager_id, start_time, end_time, del_flag)
VALUES
  (10001, 'P-FIX-001', '固件项目A', '固A', '1', '1', '企业', 90001,
   'm1', '2026-01-01 00:00:00', '2026-12-31 23:59:59', '0'),
  (10002, 'P-FIX-002', '固件项目B', '固B', '1', '1', '企业', 90001,
   'm1', '2026-01-01 00:00:00', '2026-12-31 23:59:59', '0'),
  (10003, 'P-FIX-003', '已删项目', '删', '1', '1', '企业', 90001,
   'm1', '2026-01-01 00:00:00', '2026-12-31 23:59:59', '1');
