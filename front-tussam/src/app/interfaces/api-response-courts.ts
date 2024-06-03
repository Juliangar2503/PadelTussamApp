import { Court } from "./court";

export interface ApiResponseCourts {
    data: Court[];
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
