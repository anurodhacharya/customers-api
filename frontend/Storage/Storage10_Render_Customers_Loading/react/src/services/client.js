import axios from 'axios';

export const getCustomers = async () => {
    try {
        const customers = await axios.get('http://localhost:8080/api/v1/customers');
        return customers;
    } catch (e) {
        throw e;
    }
}