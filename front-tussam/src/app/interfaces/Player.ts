

export interface Player {
  id: number;
  name: string;
  lastName: string;
  email: string;
  userName?: string;
  avatar?: string;
  points?: number;
  createdAt: string;
  leagueId?: number | null;
  roleId?: number | null;
  authToken?: string | null;
}
