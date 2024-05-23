import { Match } from "./match";

export interface ApiReponseMatches {
    data: Match[];
    message: string | null;
    statusCode: {
        value: number;
        description: string;
    };
}
