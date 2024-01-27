const protobuf = require ("protobufjs");
const sizeOf = require("object-sizeof");

async function run() {
    const root = protobuf.load("./resources/player.proto");


    const PlayerMessage = (await root).lookupType("PlayerMessage");

    const obj = {
        id: 1,
        firstName: "Mohamed",
        lastName: "Sweelam",
        email: "m.s.w@hkjhdkhdkhdkjhdkjhkdh.org"
    }

    // Build for my payload using the data 
    const payload = PlayerMessage.create(obj);
    console.log(`Payload size is ${sizeOf(payload)}`);

    // Using payload , generate buffer
    const buffer = PlayerMessage.encode(payload).finish();
    console.log(`Serialized object ${buffer}`);
    console.log(`Buffer size is ${sizeOf(buffer)}`);

    const plainPlayer = PlayerMessage.decode(buffer);
    console.log(`Deserialized object ${JSON.stringify(plainPlayer, null, 2)}`);
    console.log(`Plain object size is ${sizeOf(plainPlayer)}`);
}

run();