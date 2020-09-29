CREATE TABLE state_machine (
   machine_id varchar(255),
   state varchar(255),
   state_machine_context BLOB,
   PRIMARY KEY (machine_id)
)