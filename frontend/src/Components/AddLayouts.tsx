import * as React from "react";
import {useContext} from "react";
import {LoggedInContextCookie} from "../Pages/Authentication/Authn";
import {Layout, LayoutRight} from "../Pages/Layout";



export function AddLayouts(props: { content: React.ReactNode }) {
    return (
        <div style={{ display: "flex", flexDirection: "column" }}>
            <div style={{ display: "flex", flexWrap: "wrap" }}>
                <div className="sidebar-left" style={{ flex: "0 0 200px" }}>
                    <Layout />
                </div>
                <div style={{ flex: "1 0 400px" }}>
                    {props.content}
                </div>
                <LayoutRight />
            </div>
        </div>
    );
}