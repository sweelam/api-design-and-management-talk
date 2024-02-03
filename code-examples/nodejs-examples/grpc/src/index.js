const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");
const { v4: uuidv4 } = require('uuid');

const PROTO_PATH = "./resources/observer.proto";

function fetchServiceStatus(call, callBack) {

    let response = {
        uuid: uuidv4(),
        status: "UP",
        component: "AlertManager",
        serviceName: call.request.serviceName
    };


    // Without error handling 
    callBack(null, response);
}

const startServer = () => {
    const server = new grpc.Server();


    var serviceDef = protoLoader.loadSync(PROTO_PATH);
    var observerProto = grpc.loadPackageDefinition(serviceDef).observer;

    server.addService(observerProto.ObserverStatusService.service, { getServiceStatus: fetchServiceStatus });
    server.bindAsync('0.0.0.0:50051', grpc.ServerCredentials.createInsecure(), (err, port) => {
        server.start();
    });
}

startServer();