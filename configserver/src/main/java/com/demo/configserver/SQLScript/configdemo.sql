USE configdb;
DROP TABLE IF EXISTS `PROPERTIES`;
CREATE TABLE PROPERTIES (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    APPLICATION VARCHAR(200),
    PROFILE VARCHAR(200),
    LABEL VARCHAR(200),
    PROP_KEY VARCHAR(200),
    PROP_VALUE VARCHAR(1000),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VALUE) 
VALUES
    ('configdemo', 'prod', 'main', 'configserver.type', 'Database Production Build SERVER APPLICATION'),
    ('configdemo', 'prod', 'main', 'configserver.id', '101'),
    ('configdemo', 'prod', 'main', 'configserver.version', '1.2.3'),
    ('configdemo', 'prod', 'main', 'configserver.name', 'Database-Production-Build SERVER APPLICATION');

INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VALUE) 
VALUES
    ('configdemo', 'dev', 'main', 'configserver.id', '101'),
    ('configdemo', 'dev', 'main', 'configserver.version', '1.2.3'),
    ('configdemo', 'dev', 'main', 'configserver.name', 'Database-Development-Build SERVER DB'),
    ('configdemo', 'dev', 'main', 'configserver.type', 'Database Development Build SERVER DB');
    
    
SELECT PROP_KEY, PROP_VALUE from PROPERTIES where APPLICATION="configdemo" and PROFILE="dev" and LABEL="main"