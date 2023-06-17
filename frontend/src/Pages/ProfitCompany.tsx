import * as React from "react";
import {MDBCard, MDBCardBody, MDBCardText, MDBCol, MDBContainer} from "mdb-react-ui-kit";
import {BarChart, Bar, CartesianGrid, XAxis, YAxis, Rectangle, LabelList, Label} from 'recharts';
import {Fetch} from "../Utils/useFetch";
import {useState} from "react";
import {Link} from "react-router-dom";

export function ProfitCompany() {
    const resp = Fetch(`/company/info?role=MANAGER`, 'GET');
    const [submit, setSubmit] = useState<number | undefined>(undefined);

    const handleSubmit = (id: number) => {
        setSubmit(id);
    };

    if (resp.response) {
        if (resp.response.length === 0) {
            return <>No Managing Companies</>;
        }
    }

    function FetchProfit(props: { id: number }) {
        const data = Fetch(`company/${props.id}/employees-profit`, 'GET');
        console.log(data);
        return (
            <>
                {!data.response ? (
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>
                ) : (
                    <Chart data={data.response} />
                )}
            </>
        );
    }

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <MDBCol md="6">
                <MDBCard className="mb-4 mb-md-0">
                    <MDBCardBody>
                        <MDBCardText className="mb-4">Check Profit Companies</MDBCardText>
                        {!resp.response ? (
                            <div className="loading">
                                <div className="spinner-border" role="status">
                                    <span className="sr-only">Loading...</span>
                                </div>
                            </div>
                        ) : (
                            <>
                                {resp.response.map((object: any) => (
                                    <button onClick={() => handleSubmit(object.id)} className="mb-1" style={{ fontSize: '1.2rem' }}>
                                        <MDBCardText>{object.name}</MDBCardText>
                                    </button>
                                ))}
                                {submit && <FetchProfit id={submit} />}
                            </>
                        )}
                    </MDBCardBody>
                </MDBCard>
            </MDBCol>
            <div style={{ marginTop: '2rem', display: 'flex', justifyContent: 'center' }}>
                <Link to="/user/profile">
                    <button className="btn btn-primary" style={{
                        backgroundColor: '#165d53',
                        borderColor: '#165d53',
                        color: '#ffffff',
                    }}>Back to my profile</button>
                </Link>
            </div>
        </div>
    );
}

export function Chart({ data }) {
    const chartData = data.map((object) => ({
        name: object.first.name,
        profit: object.second,
    }));


    if(chartData.length === 0){
        return(
            <div style={{ marginTop: '2rem' }}>
                <p> No employees found</p>
            </div>
            )
    }
    console.log(chartData);
    return (
        <>
             <p> Earned money by each employee in the last 30 days</p>
                <div style={{ marginTop: '2rem', display: 'flex', justifyContent: 'center' }}>
                    <BarChart width={600} height={300} layout="vertical" data={chartData} style={{ backgroundColor: '#e2d8d0' }}>
                        <CartesianGrid strokeDasharray="3 3" stroke="#e2d8d0" />
                        <XAxis type="number" stroke="#333" tickFormatter={(value) => (value === 50 ? `${value} €` : value)} />
                        <YAxis dataKey="name" type="category" stroke="#333" />
                        <Bar dataKey="profit" barSize={30} fill="#165d53" >
                            <LabelList dataKey="profit" position="right" fill="#333" formatter={(value) => `${value} €`} />
                        </Bar>
                    </BarChart>
                </div>
        </>
    );
}




