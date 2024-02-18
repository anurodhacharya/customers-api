import axios from 'axios';

export const getCustomers = async () => {
    console.log("Inside async getCustomer");
    try {
        const customers = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            getAuthConfig()
        );
        return customers;
    } catch (e) {
        throw e;
    }
}

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
    }
})

export const saveCustomer = async (customer) => {
    console.log("Inside saveCustomer");
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            customer
        )
    } catch (e) {
        throw e;
    }
}

export const deleteCustomer = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`, 
            getAuthConfig()        
        )
    } catch (e) {
        throw e;
    }
}

export const updateCustomer = async (id, customer) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            customer,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    try {
        const body = await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`, 
            usernameAndPassword
        );
        return body;
    } catch (e) {
        throw e;
    }
}
