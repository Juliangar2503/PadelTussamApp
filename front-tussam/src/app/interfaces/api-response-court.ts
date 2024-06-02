import { Court } from "./court";

export interface ApiResponseCourt {
    data: Court;
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
