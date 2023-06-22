import * as React from 'react';
import {
    useState,
    createContext,
    useEffect, useContext,
} from 'react';
import {fetchGetSession} from "./cookieHandler";

export const LoggedInContextCookie = createContext({
    loggedInState: { auth: false, token: '', role: Array<any>('guest') },
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
    const [authenticated, setAuthenticated] = useState({
        auth: false,
        token: '',
        role: Array<any>('guest')
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchGetSession((token: string, id: Array<any>) => {
            if (token) {
                setAuthenticated({ auth: true, token: token, role: id });
            } else {
                setAuthenticated({ auth: false, token: '', role: Array<any>('guest') });
            }
            setLoading(false);
        }).catch((error) => {
            console.log(error);
            setLoading(false);
        });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <LoggedInContextCookie.Provider value={{ loggedInState: authenticated }}>
            {children}
        </LoggedInContextCookie.Provider>
    );
}