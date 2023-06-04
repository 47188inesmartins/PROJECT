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
    const [authenticated, setAuthenticated] = useState({
        auth: false,
        token: '',
        role: 'guest'
    });
    const [loading, setLoading] = useState(true); // Variável de estado para controlar o estado de carregamento

    useEffect(() => {
        fetchGetSession((token: string, id: string) => {
            if (token) {
                setAuthenticated({ auth: true, token: token, role: id });
            } else {
                setAuthenticated({ auth: false, token: '', role: 'guest' });
            }
            setLoading(false); // Marcar o carregamento como concluído
        }).catch((error) => {
            console.log(error);
            setLoading(false); // Marcar o carregamento como concluído mesmo em caso de erro
        });
    }, []);

    if (loading) {
        return <div>Loading...</div>; // Renderizar um indicador de carregamento enquanto a função fetchGetSession está sendo executada
    }

    return (
        <LoggedInContextCookie.Provider value={{ loggedInState: authenticated }}>
            {children}
        </LoggedInContextCookie.Provider>
    );
}