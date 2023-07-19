import * as React from 'react';
import {
    useState,
    createContext,
    useEffect,
} from 'react';
import {fetchGetSession} from "./cookieHandler";

export const LoggedInContextCookie = createContext({
    loggedInState: { role: Array<any>('guest') },
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
    const [authenticated, setAuthenticated] = useState({
        role: Array<any>('guest')
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchGetSession((token: string, id: Array<any>) => {
            if (token) {
                setAuthenticated({ role: id });
            } else {
                setAuthenticated({ role: Array<any>('guest') });
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