import { League } from "./league";

export interface ApiResponseLeague {
    data: League;
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
