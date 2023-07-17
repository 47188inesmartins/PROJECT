import * as React from "react";
import {useContext} from "react";
import {LoggedInContextCookie} from "../Pages/Authentication/Authn";
import {Layout, LayoutRight} from "../Pages/Layout";



export function AddLayouts(props:{content: React.ReactNode}){
    return(
                <div>
                    <div className="sidebar-left">
                        <Layout />
                    </div>
                    {props.content}
                    <LayoutRight/>
                </div>
    );
}