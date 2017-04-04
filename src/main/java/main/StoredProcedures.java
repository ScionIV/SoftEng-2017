package main;

public class StoredProcedures
{
	//initial schema to setup the database
	//Define tables here in the order they should be created:

	private static final String[] schema = {
			"CREATE TABLE Nodes ("
					+ "nodeID integer PRIMARY KEY , nodeX  DOUBLE PRECISION , nodeY  DOUBLE PRECISION)",
			"CREATE TABLE Edges ("
					+"node1 integer references Nodes(nodeID) NOT NULL"
					+" , node2 integer references Nodes(nodeID) NOT NULL)",
			"CREATE TABLE Rooms ("
					+"roomName        varchar(200) PRIMARY KEY"
					+" , roomDescription varchar(1000)"
					+" , nodeID          integer references Nodes(nodeID))",
			"CREATE TABLE Employees ("
					+"employeeID        integer PRIMARY KEY"
					+" , employeeGivenName varchar(100)"
					+" , employeeSurname   varchar(100)"
					+" , employeeTitle     varchar(100))",
			"CREATE TABLE EmployeeRooms ("
					+"roomName   varchar(200) references Rooms(roomName)"
					+" , employeeID integer references Employees(employeeID)"
					+" , constraint EmployeeRooms_pk PRIMARY KEY (roomName, employeeID))"
	};
	//initial data that will be in the database upon construction
	private static final String[] initialData = {
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3600, 700, 1)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3700, 700, 2)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3800, 800, 3)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3900, 900, 4)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3675, 1150, 5)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3700, 1050, 6)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3650, 1050, 7)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3400, 1200, 8)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3650, 1300, 9)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3650, 1400, 10)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3650, 1350, 11)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3600, 1400, 12)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3500, 1500, 13)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3400, 1700, 14)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3500, 1900, 15)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(1700, 2100, 16)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(1900, 2000, 17)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(2300, 2200, 18)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(2400, 2200, 19)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3700, 1300, 20)",
			"INSERT INTO Nodes (nodeX, nodeY, nodeid) VALUES(3900, 2200, 21)",

			"INSERT INTO Edges VALUES(1, 9)",
			"INSERT INTO Edges VALUES(9, 1)",
			"INSERT INTO Edges VALUES(8, 5)",
			"INSERT INTO Edges VALUES(5, 8)",
			"INSERT INTO Edges VALUES(7, 12)",
			"INSERT INTO Edges VALUES(12, 7)",
			"INSERT INTO Edges VALUES(16, 3)",
			"INSERT INTO Edges VALUES(3, 16)",
			"INSERT INTO Edges VALUES(20, 2)",
			"INSERT INTO Edges VALUES(2, 20)",
			"INSERT INTO Edges VALUES(13, 21)",
			"INSERT INTO Edges VALUES(21, 13)",

			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4A', 1, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4B', 2, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4C', 3, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4D', 4, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('Atrium Ele', 5, 'Elevators Room')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4RestroomAUnisex', 6, 'A bathroom')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4RestroomBUnisex', 7, 'A bathroom')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4E', 8, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4L', 9, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4K', 10, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4G', 11, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4H', 12, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4I', 13, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4J', 14, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('Hillside Ele', 15, 'Elevators Room')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('TymanConfCent', 16, 'Tyman Conference Center')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4N', 17, 'Maintenance Closet')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4RestroomMale', 18, 'Male Bathroom')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4RestroomFemale', 19, 'Female Bathroom')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4F', 20, 'Storage of Patients')",
			"INSERT INTO Rooms (roomName, nodeID, roomDescription) VALUES('4S', 21, 'Storage of Patients')",

			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Carla', 'Green', 1, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Silas', 'Odoom', 2, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Peter', 'Wong', 3, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Willis', 'Smith', 4, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Jess', 'Johnson', 5, 'NURSE')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Bill', 'Willis', 6, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Vlad', 'Jones', 7, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Oscar', 'Lee', 8, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Edward', 'Wilson', 9, 'NURSE')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Louis', 'Moore', 10, 'NURSE')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Dean', 'Thomas', 11, 'Dr.')",
			"INSERT INTO Employees (employeeGivenName, employeeSurname, employeeID, employeeTitle) VALUES('Albert', 'Young', 12, 'Dr.')",

			"INSERT INTO EmployeeRooms Values('4A', 1)",
			"INSERT INTO EmployeeRooms Values('4B', 2)",
			"INSERT INTO EmployeeRooms Values('4C', 3)",
			"INSERT INTO EmployeeRooms Values('4D', 4)",
			"INSERT INTO EmployeeRooms Values('4E', 8)",
			"INSERT INTO EmployeeRooms Values('4L', 9)",
			"INSERT INTO EmployeeRooms Values('4K', 10)",
			"INSERT INTO EmployeeRooms Values('4G', 11)",
			"INSERT INTO EmployeeRooms Values('4H', 12)",
			"INSERT INTO EmployeeRooms Values('4I', 5)",
			"INSERT INTO EmployeeRooms Values('4J', 6)",
			"INSERT INTO EmployeeRooms Values('TymanConfCent', 5)",
			"INSERT INTO EmployeeRooms Values('4F', 7)",
			"INSERT INTO EmployeeRooms Values('4S', 3)"
	};


	public static String[] getSchema() {
		return StoredProcedures.schema;
	}

	public static String[] getInitialData(){
		return StoredProcedures.initialData;
	}

	//Stored procedures below this line
	//format: procOperationDataQualifier

	public static String procRetrieveNodes(){
		//query needs work
		return "SELECT * FROM Nodes";
	}

	public static String procRetrieveNodeID(int id){
		//query needs work
		return "SELECT * FROM Nodes WHERE nodeID='"+id+"'";
	}

	public static String procInsertNode(int id, double nodeX, double nodeY){
		//query needs work
		return "INSERT INTO Nodes (nodeID, nodeX, nodeY) VALUES("+id+", "+nodeX+", "+nodeY+")";
	}

	public static String procRetrieveRooms(){
		//query needs work
		return "SELECT * FROM Rooms";
	}

	public static String procRetrieveRoomName(String roomName){
		//query needs work
		return "SELECT * FROM Nodes WHERE roomName ='"+roomName+"'";
	}

	public static String procIncertRoom(String roomName, String roomDescription, int id){
		//query needs work
		return "INSERT INTO Rooms (roomName, roomDescription, nodeID) VALUES("+roomName+","+roomDescription+","+id+")";
	}

	public static String procRetrieveEdges(){
		//query needs work
		return "SELECT * FROM Edges";
	}

	public static String procRetrieveEdge(int node1,int node2){
		//query needs work
		return "SELECT * FROM Nodes WHERE node1 ='"+node1+"' AND node2 = '"+node2+"'";
	}

	public static String procInsertRoom(int node1, int node2){
		//query needs work
		return "INSERT INTO Rooms (node1, node2) VALUES("+node1+","+node2+")";
	}

	public static String procRetrieveEmployees(){
		//query needs work
		return "SELECT * FROM Employees";
	}

	public static String procRetrieveEmployeeID(int id){
		//query needs work
		return "SELECT * FROM Employee WHERE employeeID='"+id+"'";
	}

	public static String procIncertEmployee(int id, String GN, String SN, String Title){
		//query needs work
		return "INSERT INTO Employees(employeeID,employeeGivenName,employeeSurname,employeeTitle) VALUES("+id+","+GN+","+SN+","+Title+")";
	}

	public static String procRetrieveEmployeeRooms(){
		//query needs work
		return "SELECT * FROM EmployeeRooms";
	}

	public static String procRetrieveNameID(int id, String name){
		//query needs work
		return "SELECT * FROM Employee WHERE employeeID='"+id+"' AND roomName = '"+name+"'";
	}

	public static String procInsertNameID(int id, String name){
		//query needs work
		return "INSERT INTO Employees(employeeID,roomName) VALUES("+id+","+name+")";
	}

	public static String procRetrieveNodesAndRooms(){
		return "SELECT * FROM Nodes LEFT OUTER JOIN Rooms on rooms.NODEID = nodes.NODEID";
	}
}