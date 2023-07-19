import * as React from "react";
import {useContext} from "react";
import {LoggedInContextCookie} from "../views/Authentication/Authn";
import {Layout, LayoutRight} from "../Pages/Layout";


export function AccessDenied(props:{company,role:string[], content: React.ReactNode}){
    const role = useContext(LoggedInContextCookie).loggedInState.role
    const roleCheck = role.some(r => (r.companyId === parseInt(props.company) && props.role.includes(r.role)))
    return(
        <>
            {!roleCheck?
                <div>
                    <div className="sidebar-left">
                        <Layout />
                    </div>
                    <div style={{ marginTop: '50px' }} >
                        <p style={{ color: 'black', fontSize: '40px' }}>
                            <strong>Access denied</strong>
                        </p>
                        <p style={{ color: 'black', fontSize: '30px' }}>
                            <strong>You can't access this resource</strong>
                        </p>
                    </div>
                    <LayoutRight/>
                </div>:
                props.content
            }
        </>
    );
}