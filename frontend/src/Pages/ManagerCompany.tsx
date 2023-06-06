import * as React from 'react';
import {useContext, useState} from "react";
import { ScrollView, Text, View } from 'react-native';
import {subDays, addDays, subHours, addHours} from 'date-fns';
import Timetable from "react-native-calendar-timetable";
import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
import {MDBListGroup, MDBListGroupItem} from "mdb-react-ui-kit";


export function MyCompany() {
    const a = useParams()
    const res = Fetch(`/company/${a.id}`,'GET')
    return(
        <div>
                {!res.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>
                        :<>
                            <h1>{res.response.name}</h1>
                        </>
                }
        </div>
    );
}

