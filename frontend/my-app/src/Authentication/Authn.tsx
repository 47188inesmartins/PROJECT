import * as React from 'react';
import {
    useState,
    createContext,
    useContext, useEffect,
} from 'react';


export const tokenCookie = 'cookie'
export const usernameCookie = 'cookie-name'
export const idPlayerCookie = 'cookie-id'
const url = 'background_image.jpg'

export type User = {
    id: any,
    username: string
}

type AuthContextType = {
    user: User | undefined,
    setUser: (newUserInfo: User | undefined) => void
};

export const LoggedInContext = createContext<AuthContextType>({
    user: undefined,
    setUser: () => {}
});

export type SessionState = {
    state: boolean,
    auth: boolean
}

export const LoggedInContextCookie = createContext({
    loggedInState: { state: false, auth: false, token: "" },
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
    /* const id = getCookie(idPlayerCookie);
     const user = getCookie(tokenCookie);

     const [data, setData] = React.useState(undefined);

     React.useEffect(() => {
         const token = checkCookie("")
             setData(token)
     }, [data]);

     const info = id !== undefined && user !== undefined ? { id: parseInt(id), username : user } : undefined;

     const [userInfo, setUserInfo] = useState(info);*/

    const [authenticated, setAuthenticated] = useState({ state: false, auth: false, token:""})
    useEffect(() => {
        fetchGetSession(
            (token: string) => {
                console.log("fetchgetsession", token)
                if (token) setAuthenticated({ state: true, auth: true , token: token})
                else setAuthenticated({ state: false, auth: false, token: ""})

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


export function useCurrentUser() {
    return useContext(LoggedInContext).user;
}

export function useSetUser() {
    return useContext(LoggedInContext).setUser;
}

export async function fetchGetSession(onSuccess: (token: string) => void) {
    console.log("fetch get session")
    try {
        const resp = await fetch('/user/check-session', {  credentials: 'include'})
        const response = await resp.json()
        console.log("antes do token")
        const token = response.find((item: { name: string; }) => item.name === "token")

        console.log("filter token", token)
        /*const id = response.find((it: { name: string; }) => it.name === "id")
        console.log("filter id", token)*/
        onSuccess(token.value)
    }catch (error){

    }
}