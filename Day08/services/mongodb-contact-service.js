const { MongoClient, ObjectId } = require('mongodb');
const url = 'mongodb://localhost'
const requiredFields = ['firstname', 'email', 'phone', 'city'];

module.exports.getContactById = async (id) => {
    if (!id || typeof id !== 'string') {
        throw 'id was not supplied or was not a string';
    }
    try {
        const _id = ObjectId(id);
        const conn = await MongoClient.connect(url, { useNewUrlParser: true });
        const contacts = conn.db('trainingdb').collection('contacts');
        const c1 = await contacts.findOne({ _id });
        conn.close();
        return c1;
    }
    catch (err) {
        throw err;
    }

}

module.exports.addNewContact = async (contact) => {
    if (!contact || typeof contact !== 'object') {
        throw 'contact was not supplied or was not an object!';
    }
    const missingFields = [];
    requiredFields.forEach(f => {
        if (!(f in contact)) {
            missingFields.push(f);
        }
    });
    if (missingFields.length) {
        throw 'required fields ' + missingFields.join(', ') + ' missing';
    }
    const conn = await MongoClient.connect(url, { useNewUrlParser: true });
    const contacts = conn.db('trainingdb').collection('contacts');
    const response = await contacts.insertOne(contact);
    conn.close();
    return response.insertedId;
}

module.exports.updateContact = async (contact) => { }
module.exports.deleteContact = async (id) => { }
module.exports.getAllContacts = async (options) => { }