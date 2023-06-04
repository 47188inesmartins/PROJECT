import * as React from 'react';
import {
    useState,
    createContext,
    useEffect, useContext,
} from 'react';
import {fetchGetSession} from "./cookieHandler";

export const LoggedInContextCookie = createContext({
    loggedInState: { auth: false, token: '', role: 'guest' },
});


export function AuthnContainer({ children }: { children: React.ReactNode }) {

    const [authenticated, setAuthenticated] = useState({auth: false, token: '', role: 'guest'})
    console.log("here authn")
    useEffect(() => {
        fetchGetSession(
            (token: string, id: string) => {
                if (token) {
                    setAuthenticated({ auth: true , token: token, role: id})}
                else {
                    setAuthenticated({ auth: false, token: '', role: 'guest' })}
            }
        )
        return () => { }
    }, [setAuthenticated])

    return (
        <LoggedInContextCookie.Provider value={{loggedInState: authenticated}}>
            { children }
        </LoggedInContextCookie.Provider>
    );
}