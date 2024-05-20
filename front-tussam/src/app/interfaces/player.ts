export interface Player {
    id: number;
    name: string;
    lastName: string;
    email: string;
    location?: string;
    nickname: string;
    avatar?: string;
    points?: number;
    active: boolean;
    createdAt: string;
    leagueId?: number;
    roleId?: number;
    authToken?: string | null;
}
