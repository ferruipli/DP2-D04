start transaction;

use `Acme-Rookies`;

revoke all privileges on `Acme-Rookies`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Rookies`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Rookies`;

commit;