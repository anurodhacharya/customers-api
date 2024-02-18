import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";

import { login as performLogin } from "../../services/client";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext({});

const AuthProvider = ({ children }) => {
    
    const [customer, setCustomer] = useState(null);

    const setCustomerFromToken = () => {
        let token = localStorage.getItem('access_token');
        if(token) {
            token = jwtDecode(token);
            setCustomer({
                'username': token.sub,
                'roles': token.scopes
            })
            console.log("Inside useEffect within context");
        }
    }

    useEffect(() => {
        setCustomerFromToken()
    }, []);



    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                const jwtToken = res.headers.authorization;
                localStorage.setItem("access_token", jwtToken);

                const decodedToken = jwtDecode(jwtToken);

                setCustomer({
                    'username': decodedToken.sub,
                    'roles': decodedToken.scopes
                })
                // console.log("Setting Customer in Context");

                // setCustomer({
                //     ...res.data.customerDTO
                // })
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }

    const logout = () => {
        localStorage.removeItem('access_token');
        setCustomer(null);
        // console.log("Within logout of Context");
    }

    const isCustomerAuthenticated = () => {
        const token = localStorage.getItem('access_token');
        if(!token) {
            return false;
        }

        const { exp: expiration } = jwtDecode(token);
        console.log(jwtDecode(token));
        if(Date.now() > expiration * 1000) {
            logout()
            return false;
        }

        return true;
    }

    return (
        <AuthContext.Provider value={{
            customer,
            login,
            logout,
            isCustomerAuthenticated,
            setCustomerFromToken
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export const useAuth = () => useContext(AuthContext);

export default AuthProvider;