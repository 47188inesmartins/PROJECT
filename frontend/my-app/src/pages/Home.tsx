import {Fetch} from "../useFetch";
import {Login} from "./Login";
import React from "react";
import {Layout} from "../Layout";

export function Home() {
    const a = Fetch("/","GET")
    console.log(a)
    return (
        <div >
            <Layout />
            {!a.response?
                <a> aa </a>
                :
                <a> {a.response.message}</a>
            }

        </div>


    );
}