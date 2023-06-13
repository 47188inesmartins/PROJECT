import * as React from "react";
import { CDBContainer } from 'cdbreact';
import {useState} from "react";
import {MDBContainer} from "mdb-react-ui-kit";
import { BarChart, Bar, CartesianGrid, XAxis, YAxis } from 'recharts';
import {Fetch} from "../Utils/useFetch";

interface UserMoney {
    name: string;
    profit: number;
}

export function ProfitCompany(){
    const res = Fetch("company/3/employees-profit","GET")
    const [money, SetMoney] = useState([]);


    if(res.response){
        const chartData = res.response.map((object) => ({
            name: object.first.name,
            profit: object.second,
        }));
        SetMoney(chartData)
    }

    return (
        <BarChart width={600} height={300} layout="vertical" data={money}>
            <CartesianGrid strokeDasharray="3 3" />
            <YAxis dataKey="name" type="category" />
            <XAxis type="number" />
            <Bar dataKey="profit" fill="#8884d8" />
        </BarChart>
    );
}
